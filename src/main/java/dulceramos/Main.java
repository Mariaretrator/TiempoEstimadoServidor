package dulceramos;

import dulceramos.adaptadores.red.AdaptadorControlServidorRed;
import dulceramos.adaptadores.red.AdaptadorNotificacionEvento;
import dulceramos.adaptadores.red.AdaptadorSalidaUdp;
import dulceramos.adaptadores.red.CanalUdp;
import dulceramos.adaptadores.red.mapper.UdpNetworkMapper;
import dulceramos.aplicacion.mapper.CalculoMapper;
import dulceramos.aplicacion.mapper.PeticionMapper;
import dulceramos.aplicacion.puertos.entrada.GestionarServidorInputPort;
import dulceramos.aplicacion.puertos.entrada.ProcesarPeticionUdpInputPort;
import dulceramos.aplicacion.servicios.GestionarServidorService;
import dulceramos.aplicacion.servicios.ProcesarPeticionUdpService;
import dulceramos.dominio.puertos.salida.ControladorServidorRedPort;
import dulceramos.dominio.puertos.salida.PuertoSalidaRed;
import dulceramos.entrypoint.gui.ServidorFrame;
import dulceramos.entrypoint.udp.ReceptorPeticionesUdp;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Punto de entrada del programa (Composition Root). Responsabilidad única:
 * Ensamblar las dependencias según los principios SOLID y la Arquitectura Hexagonal,
 * y lanzar la interfaz de usuario.
 */
public class Main {

    private static final System.Logger LOG = System.getLogger(Main.class.getName());

    private Main() {
        // Evita instanciación – clase de arranque estática
    }

    public static void main(final String[] args) {
        aplicarLookAndFeel();

        // 1. Infraestructura de red compartida
        final CanalUdp canalUdp = new CanalUdp();
        final AdaptadorNotificacionEvento notificador = new AdaptadorNotificacionEvento();
        final UdpNetworkMapper redMapper = new UdpNetworkMapper();
        final PuertoSalidaRed puertoSalidaRed = new AdaptadorSalidaUdp(canalUdp, redMapper, notificador);

        // 2. Mappers de aplicación
        final PeticionMapper peticionMapper = new PeticionMapper();
        final CalculoMapper calculoMapper = new CalculoMapper();

        // 3. Casos de uso (servicios de aplicación)
        final ProcesarPeticionUdpInputPort procesarPeticionPort = new ProcesarPeticionUdpService(
                puertoSalidaRed, notificador, peticionMapper, calculoMapper);

        // 4. Entrypoints
        final ReceptorPeticionesUdp receptorUdp = new ReceptorPeticionesUdp(
                canalUdp, procesarPeticionPort, notificador);

        final ControladorServidorRedPort controladorRed = new AdaptadorControlServidorRed(
                canalUdp, receptorUdp::iniciar, receptorUdp::detener);

        final GestionarServidorInputPort gestionarServidorPort = new GestionarServidorService(
                controladorRed, notificador);

        // 5. Lanzar GUI en el Event Dispatch Thread de Swing
        SwingUtilities.invokeLater(
                () -> {
                    final ServidorFrame frame = new ServidorFrame(gestionarServidorPort);
                    notificador.registrarObservador(frame);
                    frame.setVisible(true);
                });
    }

    private static void aplicarLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (final ClassNotFoundException
                       | InstantiationException
                       | IllegalAccessException
                       | UnsupportedLookAndFeelException excepcion) {
            LOG.log(System.Logger.Level.DEBUG,
                    "No fue posible aplicar la apariencia del sistema; se usará la predeterminada.", excepcion);
        }
    }
}
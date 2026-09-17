package dulceramos.aplicacion.servicios;

import dulceramos.aplicacion.excepciones.ServidorRedException;
import dulceramos.aplicacion.puertos.entrada.GestionarServidorInputPort;
import dulceramos.dominio.enums.EstadoServidor;
import dulceramos.dominio.modelos.EventoServidor;
import dulceramos.dominio.puertos.salida.ControladorServidorRedPort;
import dulceramos.dominio.puertos.salida.PuertoNotificacionEvento;
import dulceramos.dominio.vo.PuertoRed;
import java.util.Objects;

/**
 * Servicio de aplicación que implementa el caso de uso de gestión del ciclo de
 * vida del servidor.
 * Responsabilidad única: Coordinar el inicio y parada del servicio de red y
 * notificar los eventos correspondientes.
 */
public final class GestionarServidorService implements GestionarServidorInputPort {

    private final ControladorServidorRedPort controladorRed;
    private final PuertoNotificacionEvento notificador;

    public GestionarServidorService(
            final ControladorServidorRedPort controladorRed, final PuertoNotificacionEvento notificador) {
        this.controladorRed = Objects.requireNonNull(controladorRed, "El controlador de red es obligatorio.");
        this.notificador = Objects.requireNonNull(notificador, "El notificador es obligatorio.");
    }


    public void iniciarServidor(final int puerto) throws ServidorRedException {
        final PuertoRed puertoVo = new PuertoRed(puerto);
        controladorRed.iniciar(puertoVo);

        notificador.notificarCambioEstado(EstadoServidor.EN_LINEA, puerto);
        notificador.notificarEvento(
                new EventoServidor(
                        "SERVICIO", "LOCAL:" + puerto, "Servidor UDP iniciado en el puerto " + puerto + "."));
    }


    public void detenerServidor() {
        controladorRed.detener();

        notificador.notificarCambioEstado(EstadoServidor.DETENIDO, 0);
        notificador.notificarEvento(
                new EventoServidor("SERVICIO", "LOCAL", "Servidor UDP detenido por el usuario."));
    }


    public boolean estaCorriendo() {
        return controladorRed.isActivo();
    }

        public int getPuertoActual() {
        final PuertoRed puerto = controladorRed.getPuertoActual();
        return Objects.nonNull(puerto) ? puerto.valor() : 0;
    }
}
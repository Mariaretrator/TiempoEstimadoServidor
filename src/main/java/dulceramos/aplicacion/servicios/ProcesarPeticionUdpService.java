package dulceramos.aplicacion.servicios;

import dulceramos.aplicacion.dto.ProcesarPeticionUdpCommand;
import dulceramos.aplicacion.mapper.CalculoMapper;
import dulceramos.aplicacion.mapper.PeticionMapper;
import dulceramos.aplicacion.puertos.entrada.ProcesarPeticionUdpInputPort;
import dulceramos.dominio.excepciones.DominioException;
import dulceramos.dominio.modelos.Calculo;
import dulceramos.dominio.modelos.EventoServidor;
import dulceramos.dominio.modelos.RespuestaCliente;
import dulceramos.dominio.modelos.Resultado;
import dulceramos.dominio.puertos.salida.PuertoNotificacionEvento;
import dulceramos.dominio.puertos.salida.PuertoSalidaRed;
import dulceramos.dominio.vo.Destinatario;
import java.util.Locale;
import java.util.Objects;


public final class ProcesarPeticionUdpService implements ProcesarPeticionUdpInputPort {

    private final PuertoSalidaRed puertoSalidaRed;
    private final PuertoNotificacionEvento puertoNotificacion;
    private final PeticionMapper peticionMapper;
    private final CalculoMapper calculoMapper;

    public ProcesarPeticionUdpService(
            final PuertoSalidaRed puertoSalidaRed,
            final PuertoNotificacionEvento puertoNotificacion,
            final PeticionMapper peticionMapper,
            final CalculoMapper calculoMapper) {
        this.puertoSalidaRed = Objects.requireNonNull(puertoSalidaRed, "El puerto de salida es obligatorio.");
        this.puertoNotificacion = Objects.requireNonNull(puertoNotificacion, "El puerto de notificación es obligatorio.");
        this.peticionMapper = Objects.requireNonNull(peticionMapper, "El mapper de petición es obligatorio.");
        this.calculoMapper = Objects.requireNonNull(calculoMapper, "El mapper de cálculo es obligatorio.");
    }


    public void procesar(final ProcesarPeticionUdpCommand comando) {
        Objects.requireNonNull(comando, "El comando es obligatorio.");

        final Destinatario destinatario = peticionMapper.toDestinatario(comando);
        final String mensaje = comando.mensaje();

        if (Objects.isNull(mensaje) || mensaje.isBlank()) {
            puertoSalidaRed.enviarRespuesta(
                    RespuestaCliente.error(destinatario, "Mensaje vacío recibido."));
            notificarEvento(destinatario.endpoint(), "datos recibidos --> [Vacío]");
            return;
        }

        final String comandoTexto = mensaje.trim();

        if (comandoTexto.equalsIgnoreCase("CONECTAR")) {
            puertoSalidaRed.enviarRespuesta(
                    RespuestaCliente.conectado(
                            destinatario, "Servidor UDP listo para recibir cálculos de tiempo de viaje"));
            notificarEvento(
                    destinatario.endpoint(),
                    "conectado --> Solicitud de verificación recibida y aceptada");
            return;
        }

        if (comandoTexto.equalsIgnoreCase("DESCONECTAR")) {
            puertoSalidaRed.enviarRespuesta(
                    RespuestaCliente.desconectado(destinatario, "Sesión finalizada"));
            notificarEvento(destinatario.endpoint(), "desconectado --> Cliente ha cerrado la sesión");
            return;
        }

        if (comandoTexto.toUpperCase(Locale.ROOT).startsWith("CALCULAR;")) {
            procesarCalculo(destinatario, comandoTexto);
            return;
        }

        puertoSalidaRed.enviarRespuesta(
                RespuestaCliente.error(destinatario, "Comando no reconocido por el servidor UDP."));
        notificarEvento(
                destinatario.endpoint(),
                "datos recibidos --> Comando desconocido: [" + comandoTexto + "]");
    }

    private void procesarCalculo(final Destinatario destinatario, final String comandoTexto) {
        final String[] partes = comandoTexto.split(";", -1);
        if (partes.length != 3) {
            puertoSalidaRed.enviarRespuesta(
                    RespuestaCliente.error(
                            destinatario, "Formato inválido. Se esperaba CALCULAR;distancia;velocidad"));
            notificarEvento(
                    destinatario.endpoint(),
                    "datos recibidos --> Formato incorrecto: " + comandoTexto);
            return;
        }

        try {
            final double valorDistancia = Double.parseDouble(partes[1].trim().replace(',', '.'));
            final double valorVelocidad = Double.parseDouble(partes[2].trim().replace(',', '.'));

            final Calculo calculo = calculoMapper.toDomain(valorDistancia, valorVelocidad);
            final Resultado resultado = calculo.calcular();

            puertoSalidaRed.enviarRespuesta(RespuestaCliente.calculoExitoso(destinatario, resultado));

            final String logInfo = String.format(
                    Locale.US,
                    "datos recibidos (Distancia: %.2f km, Velocidad: %.2f km/h) --> datos enviados (Tiempo: %s, %s)",
                    calculo.getDistancia().valor(),
                    calculo.getVelocidad().valor(),
                    resultado.getTiempoFormateado(),
                    resultado.getClasificacion());

            notificarEvento(destinatario.endpoint(), logInfo);

        } catch (final NumberFormatException excepcion) {
            puertoSalidaRed.enviarRespuesta(
                    RespuestaCliente.error(
                            destinatario, "Los parámetros de distancia y velocidad deben ser numéricos."));
            notificarEvento(
                    destinatario.endpoint(),
                    "datos recibidos --> Error de formato numérico: " + comandoTexto);

        } catch (final DominioException excepcion) {
            puertoSalidaRed.enviarRespuesta(RespuestaCliente.error(destinatario, excepcion.getMessage()));
            notificarEvento(
                    destinatario.endpoint(),
                    "datos recibidos --> Validación fallida: " + excepcion.getMessage());
        }
    }

    private void notificarEvento(final String endpoint, final String descripcion) {
        puertoNotificacion.notificarEvento(new EventoServidor("EVENTO", endpoint, descripcion));
    }
}
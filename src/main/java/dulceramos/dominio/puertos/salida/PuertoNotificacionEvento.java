package dulceramos.dominio.puertos.salida;

import dulceramos.dominio.enums.EstadoServidor;
import dulceramos.dominio.modelos.EventoServidor;


public interface PuertoNotificacionEvento {
    void notificarEvento(EventoServidor evento);

    void notificarCambioEstado(EstadoServidor nuevoEstado, int puerto);
}
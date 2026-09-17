package dulceramos.adaptadores.red;

import dulceramos.dominio.enums.EstadoServidor;
import dulceramos.dominio.modelos.EventoServidor;


public interface ObservadorServidor {
    void onEvento(EventoServidor evento);

    void onCambioEstado(EstadoServidor nuevoEstado, int puerto);
}
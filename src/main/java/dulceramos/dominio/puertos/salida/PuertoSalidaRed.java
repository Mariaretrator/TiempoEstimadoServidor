package dulceramos.dominio.puertos.salida;

import dulceramos.dominio.modelos.RespuestaCliente;


public interface PuertoSalidaRed {
    void enviarRespuesta(RespuestaCliente respuesta);
}

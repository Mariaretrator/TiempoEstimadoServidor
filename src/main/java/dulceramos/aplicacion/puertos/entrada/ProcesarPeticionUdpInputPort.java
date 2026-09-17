package dulceramos.aplicacion.puertos.entrada;

import dulceramos.aplicacion.dto.ProcesarPeticionUdpCommand;


public interface ProcesarPeticionUdpInputPort {
    void procesar(ProcesarPeticionUdpCommand comando);
}
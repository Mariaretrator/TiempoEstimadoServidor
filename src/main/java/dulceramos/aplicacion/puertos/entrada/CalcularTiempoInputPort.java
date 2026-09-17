package dulceramos.aplicacion.puertos.entrada;

import dulceramos.aplicacion.dto.CalcularTiempoCommand;
import dulceramos.aplicacion.dto.ResultadoTiempoDto;

/** Puerto de entrada (Caso de Uso) para el cálculo del tiempo de viaje. */
public interface CalcularTiempoInputPort {
    ResultadoTiempoDto calcular(CalcularTiempoCommand comando);
}

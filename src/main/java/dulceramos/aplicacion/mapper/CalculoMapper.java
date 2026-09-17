package dulceramos.aplicacion.mapper;

import dulceramos.aplicacion.dto.CalcularTiempoCommand;
import dulceramos.aplicacion.dto.ResultadoTiempoDto;
import dulceramos.aplicacion.excepciones.ComandoInvalidoException;
import dulceramos.dominio.modelos.Calculo;
import dulceramos.dominio.modelos.Resultado;
import dulceramos.dominio.vo.Distancia;
import dulceramos.dominio.vo.Velocidad;
import java.util.Objects;

/**
 * Mapper de la capa de aplicación. Responsabilidad única: Convertir
 * comandos/DTOs de cálculo a entidades del dominio y viceversa.
 */
public final class CalculoMapper {

    public Calculo toDomain(final CalcularTiempoCommand comando) {
        if (Objects.isNull(comando)) {
            throw new ComandoInvalidoException("El comando de cálculo no puede ser nulo.");
        }
        final Distancia distancia = new Distancia(comando.distancia());
        final Velocidad velocidad = new Velocidad(comando.velocidad());
        return new Calculo(distancia, velocidad);
    }

    public Calculo toDomain(final double valorDistancia, final double valorVelocidad) {
        final Distancia distancia = new Distancia(valorDistancia);
        final Velocidad velocidad = new Velocidad(valorVelocidad);
        return new Calculo(distancia, velocidad);
    }

    public ResultadoTiempoDto toDto(final Resultado resultado) {
        if (Objects.isNull(resultado)) {
            throw new ComandoInvalidoException("El resultado del cálculo no puede ser nulo.");
        }
        return new ResultadoTiempoDto(
                resultado.getTiempo(),
                resultado.getTiempoFormateado(),
                resultado.getClasificacion(),
                resultado.getRecomendaciones());
    }
}
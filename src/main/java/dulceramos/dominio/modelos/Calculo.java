package dulceramos.dominio.modelos;

import dulceramos.dominio.enums.ClasificacionViaje;
import dulceramos.dominio.excepciones.CalculoInvalidoException;
import dulceramos.dominio.vo.Distancia;
import dulceramos.dominio.vo.Velocidad;
import java.util.Objects;

/**
 * Entidad/Modelo del dominio para el cálculo del tiempo estimado de viaje. Responsabilidad
 * única: Aplicar la fórmula matemática (tiempo = distancia / velocidad) y clasificar el trayecto.
 */
public final class Calculo {

    private final Distancia distancia;
    private final Velocidad velocidad;

    public Calculo(final Distancia distancia, final Velocidad velocidad) {
        if (Objects.isNull(distancia) || Objects.isNull(velocidad)) {
            throw new CalculoInvalidoException(
                    "La distancia y la velocidad son obligatorias para calcular el tiempo de viaje.");
        }
        this.distancia = distancia;
        this.velocidad = velocidad;
    }

    /**
     * Calcula el tiempo de viaje a partir de la distancia y la velocidad aplicando la fórmula
     * física básica y genera una recomendación de viaje.
     *
     * @return Objeto Resultado con el valor numérico, clasificación y recomendación.
     */
    public Resultado calcular() {
        // Asegúrate de que en tus records Distancia y Velocidad el método para obtener el número se llame valor()
        final double tiempo = distancia.valor() / velocidad.valor();
        final ClasificacionViaje clasificacion = ClasificacionViaje.deTiempo(tiempo);

        return new Resultado(
                tiempo, clasificacion.getClasificacion(), clasificacion.getRecomendacion(), clasificacion);
    }

    public Distancia getDistancia() {
        return distancia;
    }

    public Velocidad getVelocidad() {
        return velocidad;
    }
}


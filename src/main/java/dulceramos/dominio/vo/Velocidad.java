package dulceramos.dominio.vo;



import dulceramos.dominio.excepciones.VelocidadIncorrectaException;

/**
 * Value Object inmutable que encapsula y valida la velocidad del viaje.
 */
public record Velocidad(double valor) {

    private static final String MENSAJE_ERROR =
            "Velocidad incorrecta: debe ser mayor que 0";

    public Velocidad {
        if (!Double.isFinite(valor) || valor <= 0) {
            throw new VelocidadIncorrectaException(MENSAJE_ERROR);
        }
    }
}
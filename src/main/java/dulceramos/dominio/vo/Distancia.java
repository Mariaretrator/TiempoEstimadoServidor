package dulceramos.dominio.vo;

import dulceramos.dominio.excepciones.DistanciaIncorrectaException;

public record Distancia(double valor) {

    private static final String MENSAJE_ERROR =
            "Distancia incorrecta: debe ser mayor que 0";

    public Distancia {
        if (!Double.isFinite(valor) || valor <= 0) {
            throw new DistanciaIncorrectaException(MENSAJE_ERROR);
        }
    }
}

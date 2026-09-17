package dulceramos.dominio.vo;

import dulceramos.dominio.excepciones.PuertoIncorrectoException;

public record PuertoRed(int valor) {

    public PuertoRed {
        if (valor < 1024 || valor > 65535) {
            throw new PuertoIncorrectoException("El puerto debe ser un número entre 1024 y 65535.");
        }
    }
}

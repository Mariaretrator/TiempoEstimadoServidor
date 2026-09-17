package dulceramos.aplicacion.excepciones;

import java.io.Serial;

public final class ComandoInvalidoException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ComandoInvalidoException(final String mensaje) {
        super(mensaje);
    }
}

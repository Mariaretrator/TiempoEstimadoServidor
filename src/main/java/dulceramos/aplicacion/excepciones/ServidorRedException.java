package dulceramos.aplicacion.excepciones;

import java.io.Serial;


public final class ServidorRedException extends Exception {


    private static final long serialVersionUID = 1L;

    public ServidorRedException(final String mensaje, final Throwable causa) {
        super(mensaje, causa);
    }
}


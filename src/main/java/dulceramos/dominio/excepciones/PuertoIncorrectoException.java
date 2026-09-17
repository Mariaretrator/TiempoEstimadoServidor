package dulceramos.dominio.excepciones;

public class PuertoIncorrectoException extends DominioException {
    private static final long serialVersionUID = 1L;

    public PuertoIncorrectoException(final String mensaje) {
        super(mensaje);
    }
}


package dulceramos.dominio.excepciones;

public class CalculoInvalidoException extends DominioException {
    private static final long serialVersionUID = 1L;

    public CalculoInvalidoException(final String mensaje) {
        super(mensaje);
    }
}


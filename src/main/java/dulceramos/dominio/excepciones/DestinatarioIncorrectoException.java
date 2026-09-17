package dulceramos.dominio.excepciones;

public class DestinatarioIncorrectoException extends DominioException {
    private static final long serialVersionUID = 1L;

    public DestinatarioIncorrectoException(final String mensaje) {
        super(mensaje);
    }
}


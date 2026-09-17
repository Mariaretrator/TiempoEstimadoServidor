package dulceramos.dominio.excepciones;

public class RespuestaInvalidaException extends DominioException{
    private static final long serialVersionUID = 1L;

    public RespuestaInvalidaException(final String mensaje) {
        super(mensaje);
    }
}


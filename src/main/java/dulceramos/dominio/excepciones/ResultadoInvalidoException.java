package dulceramos.dominio.excepciones;

public class ResultadoInvalidoException extends DominioException  {
    private static final long serialVersionUID = 1L;

    public ResultadoInvalidoException(final String mensaje) {
        super(mensaje);
    }
}


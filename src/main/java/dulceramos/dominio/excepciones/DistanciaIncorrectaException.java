package dulceramos.dominio.excepciones;

public class DistanciaIncorrectaException extends DominioException {
    private static final long serialVersionUID = 1L;

    public DistanciaIncorrectaException(final String mensaje){
        super(mensaje);
    }
}



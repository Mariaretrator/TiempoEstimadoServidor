package dulceramos.dominio.excepciones;

public class VelocidadIncorrectaException  extends DominioException {
    private static final long serialVersionUID = 1L;

    public VelocidadIncorrectaException(final String mensaje) {
        super(mensaje);
    }
}


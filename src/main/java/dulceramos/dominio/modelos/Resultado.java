package dulceramos.dominio.modelos;

import dulceramos.dominio.enums.ClasificacionViaje;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Objects;
import dulceramos.dominio.excepciones.ResultadoInvalidoException;

public final class Resultado {

    private final double tiempo;
    private final String clasificacion;
    private final String recomendaciones;
    private final ClasificacionViaje clasificacionViaje;

    public Resultado(
            final double tiempo,
            final String clasificacion,
            final String recomendaciones,
            final ClasificacionViaje clasificacionViaje) {
        if (!Double.isFinite(tiempo) || Objects.isNull(clasificacion)
                || Objects.isNull(recomendaciones) || Objects.isNull(clasificacionViaje)) {
            throw new ResultadoInvalidoException("El resultado del tiempo calculado contiene datos inválidos.");
        }
        this.tiempo = tiempo;
        this.clasificacion = clasificacion;
        this.recomendaciones = recomendaciones;
        this.clasificacionViaje = clasificacionViaje;
    }

    public Resultado(final double tiempo, final String clasificacion, final String recomendaciones) {
        this(tiempo, clasificacion, recomendaciones, ClasificacionViaje.deTiempo(tiempo));
    }

    public double getTiempo() {
        return tiempo;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public String getRecomendaciones() {
        return recomendaciones;
    }

    public ClasificacionViaje getClasificacionViaje() {
        return clasificacionViaje;
    }

    public String getTiempoFormateado() {
        final DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        final DecimalFormat df = new DecimalFormat("#.##", symbols);
        return df.format(tiempo);
    }
}
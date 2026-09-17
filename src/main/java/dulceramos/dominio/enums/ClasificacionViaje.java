package dulceramos.dominio.enums;

public enum ClasificacionViaje {
    MUY_CORTO(
            "Viaje muy corto (Urbano)",
            "El viaje es breve. Conduce con precaución y respeta las señales de tránsito."),
    CORTO(
            "Viaje corto (Cercano)",
            "¡Excelente! Trayecto rápido que puedes realizar sin necesidad de detenerte."),
    MODERADO(
            "Viaje moderado (Media distancia)",
            "Se aconseja hacer al menos una pausa corta para estirar las piernas y descansar."),
    LARGO(
            "Viaje largo (Larga distancia)", "Se recomienda hacer varias pausas, mantener la hidratación y descansar la vista."),
    MUY_LARGO(
            "Viaje muy largo (Extenuante)", "Riesgo de fatiga elevado. Requiere turnarse con otro conductor si es posible."),
    EXTREMO(
            "Viaje extremo (Múltiples días)",
            "Riesgo crítico de agotamiento. Se recomienda encarecidamente pernoctar y dividir el viaje.");

    private final String clasificacion;
    private final String recomendacion;

    ClasificacionViaje(final String clasificacion, final String recomendacion) {
        this.clasificacion = clasificacion;
        this.recomendacion = recomendacion;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public String getRecomendacion() {
        return recomendacion;
    }


    public static ClasificacionViaje deTiempo(final double tiempo) {
        if (tiempo < 1.0) {
            return MUY_CORTO;
        } else if (tiempo < 3.0) {
            return CORTO;
        } else if (tiempo < 5.0) {
            return MODERADO;
        } else if (tiempo < 8.0) {
            return LARGO;
        } else if (tiempo < 12.0) {
            return MUY_LARGO;
        } else {
            return EXTREMO;
        }
    }
}
package dulceramos.aplicacion.dto;

public record ProcesarPeticionUdpCommand(String ipCliente, int puertoCliente, String mensaje) {
}
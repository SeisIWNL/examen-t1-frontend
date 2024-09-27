package pe.edu.cibertec.VehiculoFront.dto;

public record VehicleResponse(
        String codigo, String placa, String marca,
        String modelo, String nro_asientos, String precio,
        String color) {
}
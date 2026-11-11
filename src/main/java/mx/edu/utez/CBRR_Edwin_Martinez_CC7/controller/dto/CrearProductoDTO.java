package mx.edu.utez.CBRR_Edwin_Martinez_CC7.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CrearProductoDTO {

    private Long id;
    private String nombre;
    private String categoria;
    private Double precio;
    private Integer stock;
    private boolean activo;
}

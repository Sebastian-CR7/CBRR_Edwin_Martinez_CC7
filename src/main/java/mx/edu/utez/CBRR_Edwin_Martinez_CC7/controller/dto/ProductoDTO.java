package mx.edu.utez.CBRR_Edwin_Martinez_CC7.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {

    @NotBlank
    private String nombre;

    @NotBlank
    private String categoria;

    @NotNull
    private Double precio;

    @NotNull
    private Integer stock;



}

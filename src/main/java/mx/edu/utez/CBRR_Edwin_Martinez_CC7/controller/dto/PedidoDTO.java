package mx.edu.utez.CBRR_Edwin_Martinez_CC7.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.CBRR_Edwin_Martinez_CC7.model.EstadoPedido;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {

    @NotBlank(message = "Nombre obligatorio")
    private String nombreCliente;

    @Positive(message = "Debe ser positiva")
    private Integer cantidad;


}

package mx.edu.utez.CBRR_Edwin_Martinez_CC7.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.CBRR_Edwin_Martinez_CC7.model.EstadoPedido;
import mx.edu.utez.CBRR_Edwin_Martinez_CC7.model.Producto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CrearPedidoDTO {

    private Long id;
    private String nombreCliente;
    private Integer cantidad;
    private Double precioUnitario;
    private Double total;
    private EstadoPedido estadoPedido;
    private Producto producto;
}

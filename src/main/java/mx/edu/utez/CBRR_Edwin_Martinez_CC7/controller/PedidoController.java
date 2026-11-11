package mx.edu.utez.CBRR_Edwin_Martinez_CC7.controller;


import jakarta.validation.Valid;
import mx.edu.utez.CBRR_Edwin_Martinez_CC7.controller.dto.CrearPedidoDTO;
import mx.edu.utez.CBRR_Edwin_Martinez_CC7.controller.dto.PedidoDTO;
import mx.edu.utez.CBRR_Edwin_Martinez_CC7.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin("*")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> crearPedido(@RequestBody @Valid CrearPedidoDTO payload) {
        return ResponseEntity
                .status(201)
                .body(pedidoService.crearPedido(payload));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<PedidoDTO> cancelarPedido(@PathVariable Long id) {
        return ResponseEntity
                .status(200)
                .body(pedidoService.cancelarPedido(id));
    }

    @PatchMapping("/{id}/procesar")
    public ResponseEntity<PedidoDTO> procesarPedido(@PathVariable Long id) {
        return ResponseEntity
                .status(200)
                .body(pedidoService.procesarPedido(id));
    }

    @PatchMapping("/producto/{productoId}/cancelar-pendientes")
    public ResponseEntity<List<PedidoDTO>> cancelarPedidosPendientesDeProducto(@PathVariable Long productoId) {
        return ResponseEntity
                .status(200)
                .body(pedidoService.cancelarPedidosPendientesDeProducto(productoId));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PedidoDTO>> consultarPorEstado(@PathVariable String estado) {
        return ResponseEntity
                .status(200)
                .body(pedidoService.consultarPorEstado(estado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> consultarDetallePedido(@PathVariable Long id) {
        return ResponseEntity
                .status(200)
                .body(pedidoService.consultarDetallePedido(id));
    }
}
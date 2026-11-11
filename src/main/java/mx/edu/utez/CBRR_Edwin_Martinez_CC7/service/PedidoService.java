package mx.edu.utez.CBRR_Edwin_Martinez_CC7.service;


import mx.edu.utez.CBRR_Edwin_Martinez_CC7.controller.dto.PedidoDTO;
import mx.edu.utez.CBRR_Edwin_Martinez_CC7.model.EstadoPedido;
import mx.edu.utez.CBRR_Edwin_Martinez_CC7.model.Pedido;
import mx.edu.utez.CBRR_Edwin_Martinez_CC7.model.Producto;
import mx.edu.utez.CBRR_Edwin_Martinez_CC7.repository.PedidoRepository;
import mx.edu.utez.CBRR_Edwin_Martinez_CC7.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;
    private final ProductoService productoService;

    public PedidoService(PedidoRepository pedidoRepository, ProductoRepository productoRepository, ProductoService productoService) {
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
        this.productoService = productoService;
    }


    public PedidoDTO crearPedido(PedidoDTO payload) {
        Producto producto = productoRepository.findById(payload.getProductoId())
                .orElseThrow(() -> new RuntimeException("El producto no existe."));

        if (!producto.isActivo()) {
            throw new RuntimeException("El producto no está activo para recibir pedidos.");
        }

        if (producto.getStock() < payload.getCantidad()) {
            throw new RuntimeException("Stock insuficiente para realizar el pedido.");
        }

        Double precioUnitario = producto.getPrecio();
        Double total = precioUnitario * payload.getCantidad();

        producto.setStock(producto.getStock() - payload.getCantidad());
        productoRepository.save(producto);

        Pedido pedido = new Pedido();
        pedido.setNombreCliente(payload.getNombreCliente());
        pedido.setCantidad(payload.getCantidad());
        pedido.setPrecioUnitario(precioUnitario);
        pedido.setTotal(total);
        pedido.setEstadoPedido(EstadoPedido.PENDIENTE);
        pedido.setProducto(producto);

        return mapToDTO(pedidoRepository.save(pedido));
    }

    public PedidoDTO cancelarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El pedido con ID " + id + " no existe."));

        if (pedido.getEstado() != EstadoPedido.PENDIENTE) {
            throw new RuntimeException("Únicamente se pueden cancelar pedidos en estado PENDIENTE.");
        }

        pedido.setEstado(EstadoPedido.CANCELADO);

        Producto producto = pedido.getProducto();
        producto.setStock(producto.getStock() + pedido.getCantidad());
        productoRepository.save(producto);

        return mapToDTO(pedidoRepository.save(pedido));
    }


    public PedidoDTO procesarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El pedido con ID " + id + " no existe."));

        if (pedido.getEstado() != EstadoPedido.PENDIENTE) {
            throw new RuntimeException("Únicamente se pueden procesar pedidos en estado PENDIENTE.");
        }

        pedido.setEstado(EstadoPedido.PROCESADO);
        return mapToDTO(pedidoRepository.save(pedido));
    }

    public List<PedidoDTO> cancelarPedidosPendientesDeProducto(Long productoId) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("El producto con ID " + productoId + " no existe."));

        List<Pedido> pendientes = pedidoRepository.findByProductoIdAndEstado(productoId, EstadoPedido.PENDIENTE);

        int totalStockADevolver = 0;
        for (Pedido p : pendientes) {
            p.setEstado(EstadoPedido.CANCELADO);
            totalStockADevolver += p.getCantidad();
        }

        producto.setStock(producto.getStock() + totalStockADevolver);
        productoRepository.save(producto);

        List<Pedido> guardados = pedidoRepository.saveAll(pendientes);

        return guardados.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<PedidoDTO> consultarPorEstado(String estadoStr) {
        EstadoPedido estado;
        try {
            estado = EstadoPedido.valueOf(estadoStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Estado inválido. Los estados permitidos son: PENDIENTE, PROCESADO, CANCELADO.");
        }

        return pedidoRepository.findByEstado(estado)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public PedidoDTO consultarDetallePedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El pedido con ID " + id + " no existe."));

        return mapToDTO(pedido);
    }

    private PedidoDTO mapToDTO(Pedido pedido) {
        return new PedidoDTO(
                pedido.getId(),
                pedido.getNombreCliente(),
                pedido.getCantidad(),
                pedido.getPrecioUnitario(),
                pedido.getTotal(),
                pedido.getEstado(),
                productoService.mapToDTO(pedido.getProducto())
        );
    }
}
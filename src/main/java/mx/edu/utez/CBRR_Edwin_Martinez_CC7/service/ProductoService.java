package mx.edu.utez.CBRR_Edwin_Martinez_CC7.service;


import mx.edu.utez.CBRR_Edwin_Martinez_CC7.controller.dto.CrearProductoDTO;
import mx.edu.utez.CBRR_Edwin_Martinez_CC7.controller.dto.ProductoDTO;
import mx.edu.utez.CBRR_Edwin_Martinez_CC7.model.EstadoPedido;
import mx.edu.utez.CBRR_Edwin_Martinez_CC7.model.Producto;
import mx.edu.utez.CBRR_Edwin_Martinez_CC7.repository.PedidoRepository;
import mx.edu.utez.CBRR_Edwin_Martinez_CC7.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final PedidoRepository pedidoRepository;

    public ProductoService(ProductoRepository productoRepository, PedidoRepository pedidoRepository) {
        this.productoRepository = productoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    public ProductoDTO crearProducto(CrearProductoDTO dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setCategoria(dto.getCategoria());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setActivo(true);

        return mapToDTO(productoRepository.save(producto));
    }

    public ProductoDTO desactivarProducto(Long productoId) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("El producto con ID " + productoId + " no existe."));

        boolean tienePedidosPendientes = pedidoRepository.existsByProductoIdAndEstado(productoId, EstadoPedido.PENDIENTE);
        if (tienePedidosPendientes) {
            throw new RuntimeException("No se puede desactivar el producto porque tiene pedidos en estado PENDIENTE.");
        }

        producto.setActivo(false);
        return mapToDTO(productoRepository.save(producto));
    }

    public List<ProductoDTO> consultarPorCategoria(String categoria) {
        return productoRepository.findByCategoriaIgnoreCase(categoria)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductoDTO> listarTodos() {
        return productoRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductoDTO> listarActivos() {
        return productoRepository.findByActivoTrue()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public ProductoDTO mapToDTO(Producto producto) {
        return new ProductoDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getCategoria(),
                producto.getPrecio(),
                producto.getStock(),
                producto.isActivo()
        );
    }
}
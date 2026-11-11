package mx.edu.utez.CBRR_Edwin_Martinez_CC7.controller;

import mx.edu.utez.CBRR_Edwin_Martinez_CC7.controller.dto.ProductoDTO;
import mx.edu.utez.CBRR_Edwin_Martinez_CC7.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;

@RestController
@RequestMapping("/productos")
@CrossOrigin("*")
public class ProductoController{
    private final ProductoService productoService;


    @PostMapping
    public ProductoService













































        private final ProductoService productoService;

        public ProductoController(ProductoService productoService) {
            this.productoService = productoService;
        }

        @PostMapping
        public ResponseEntity<ProductoDTO> crearProducto(@RequestBody @Valid CrearProductoDTO dto) {
            return ResponseEntity
                    .status(201)
                    .body(productoService.crearProducto(dto));
        }

        @PatchMapping("/{id}/desactivar")
        public ResponseEntity<ProductoDTO> desactivarProducto(@PathVariable Long id) {
            return ResponseEntity
                    .status(200)
                    .body(productoService.desactivarProducto(id));
        }

        @GetMapping("/categoria/{categoria}")
        public ResponseEntity<List<ProductoDTO>> consultarPorCategoria(@PathVariable String categoria) {
            return ResponseEntity
                    .status(200)
                    .body(productoService.consultarPorCategoria(categoria));
        }

        @GetMapping
        public ResponseEntity<List<ProductoDTO>> listarTodos() {
            return ResponseEntity
                    .status(200)
                    .body(productoService.listarTodos());
        }

        @GetMapping("/activos")
        public ResponseEntity<List<ProductoDTO>> listarActivos() {
            return ResponseEntity
                    .status(200)
                    .body(productoService.listarActivos());
        }
}


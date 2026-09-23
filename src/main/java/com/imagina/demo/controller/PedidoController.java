package com.imagina.demo.controller;

import com.imagina.demo.model.Pedido;
import com.imagina.demo.service.ClienteService;
import com.imagina.demo.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// NOTA (issue plantado #4 - endpoints sin test): esta clase entera se añade en
// la PR y no tiene ningún test de integración asociado, a diferencia de
// ClienteController que sí lo tenía desde la rama main.
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final ClienteService clienteService;

    public PedidoController(PedidoService pedidoService, ClienteService clienteService) {
        this.pedidoService = pedidoService;
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<Pedido> listar() {
        return pedidoService.listarTodos();
    }

    @PostMapping
    public Pedido crear(@RequestBody Pedido pedido) {
        return pedidoService.crear(pedido);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clienteService.eliminarPedido(id);
        return ResponseEntity.noContent().build();
    }
}

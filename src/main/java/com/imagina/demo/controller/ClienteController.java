package com.imagina.demo.controller;

import com.imagina.demo.model.Cliente;
import com.imagina.demo.repository.ClienteRepository;
import com.imagina.demo.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteRepository clienteRepository;
    private final JdbcTemplate jdbcTemplate;

    public ClienteController(ClienteService clienteService, ClienteRepository clienteRepository, JdbcTemplate jdbcTemplate) {
        this.clienteService = clienteService;
        this.clienteRepository = clienteRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    public List<Cliente> listar() {
        return clienteService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtener(@PathVariable Long id) {
        return clienteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Cliente crear(@RequestBody Cliente cliente) {
        return clienteService.crear(cliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // NOTA (issue plantado #3 - problema N+1): por cada cliente se accede a
    // getPedidos(), que es una colección LAZY. Esto dispara una consulta SQL
    // adicional por cliente en vez de una única consulta con JOIN FETCH.
    @GetMapping("/con-pedidos")
    public List<Map<String, Object>> listarConPedidos() {
        return clienteService.listarConPedidos().stream()
                .map(c -> Map.<String, Object>of(
                        "cliente", c.getNombre(),
                        "numPedidos", c.getPedidos().size()))
                .collect(Collectors.toList());
    }

    // NOTA (issue plantado #1 - inyección SQL): expone al endpoint la consulta
    // insegura de ClienteRepository.buscarPorNombreInseguro.
    @GetMapping("/buscar")
    public List<Cliente> buscar(@RequestParam String nombre) {
        return clienteRepository.buscarPorNombreInseguro(jdbcTemplate, nombre);
    }
}

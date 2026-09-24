package com.imagina.demo.service;

import com.imagina.demo.model.Cliente;
import com.imagina.demo.repository.ClienteRepository;
import com.imagina.demo.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final PedidoRepository pedidoRepository;

    public ClienteService(ClienteRepository clienteRepository, PedidoRepository pedidoRepository) {
        this.clienteRepository = clienteRepository;
        this.pedidoRepository = pedidoRepository;
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente crear(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public void eliminar(Long id) {
        clienteRepository.deleteById(id);
    }

    // NOTA (issue plantado #2 - excepción silenciada): se captura la excepción
    // y no se hace nada con ella. Viola la convención de CLAUDE.md sobre manejo
    // de errores (ni se loguea con nivel adecuado, ni se relanza).
    public void eliminarPedido(Long pedidoId) {
        try {
            pedidoRepository.deleteById(pedidoId);
        } catch (Exception e) {
            // silenciado a propósito para la demo
        }
    }

    public List<Cliente> listarConPedidos() {
        return clienteRepository.findAll();
    }
}

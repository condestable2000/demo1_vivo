package com.imagina.demo.service;

import com.imagina.demo.model.Pedido;
import com.imagina.demo.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public Pedido crear(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }
}

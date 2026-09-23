package com.imagina.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String producto;

    private Double importe;

    // JsonIgnoreProperties("pedidos") corta el bucle de serialización: al
    // devolver un Pedido se incluye su Cliente, pero sin que ese Cliente
    // vuelva a serializar su lista de pedidos (que incluiría este mismo
    // Pedido, y así indefinidamente). Bug real detectado al probar
    // GET /pedidos manualmente, no uno de los 4 problemas plantados para la
    // demo.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    @JsonIgnoreProperties({"pedidos"})
    private Cliente cliente;

    public Pedido() {
    }

    public Pedido(String producto, Double importe, Cliente cliente) {
        this.producto = producto;
        this.importe = importe;
        this.cliente = cliente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}

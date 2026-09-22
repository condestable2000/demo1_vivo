package com.imagina.demo;

import com.imagina.demo.model.Cliente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClienteControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void crearYObtenerCliente() {
        Cliente nuevo = new Cliente("Ana Garcia", "ana@example.com");

        ResponseEntity<Cliente> creado = restTemplate.postForEntity("/clientes", nuevo, Cliente.class);
        assertThat(creado.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(creado.getBody()).isNotNull();
        assertThat(creado.getBody().getId()).isNotNull();

        ResponseEntity<Cliente> obtenido = restTemplate.getForEntity(
                "/clientes/" + creado.getBody().getId(), Cliente.class);
        assertThat(obtenido.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(obtenido.getBody().getNombre()).isEqualTo("Ana Garcia");
    }

    @Test
    void listarClientesDevuelveOk() {
        ResponseEntity<Cliente[]> respuesta = restTemplate.getForEntity("/clientes", Cliente[].class);
        assertThat(respuesta.getStatusCode().is2xxSuccessful()).isTrue();
    }
}

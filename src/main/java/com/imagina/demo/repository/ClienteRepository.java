package com.imagina.demo.repository;

import com.imagina.demo.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // NOTA (issue plantado #1 - inyección SQL): esta consulta se construye
    // concatenando directamente el parámetro de entrada del usuario, en vez de
    // usar @Query con un parámetro nombrado. Viola la convención de CLAUDE.md.
    default List<Cliente> buscarPorNombreInseguro(JdbcTemplate jdbcTemplate, String nombre) {
        String sql = "SELECT * FROM cliente WHERE nombre LIKE '%" + nombre + "%'";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Cliente c = new Cliente();
            c.setId(rs.getLong("id"));
            c.setNombre(rs.getString("nombre"));
            c.setEmail(rs.getString("email"));
            return c;
        });
    }
}

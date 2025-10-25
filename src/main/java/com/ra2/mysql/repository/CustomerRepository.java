package com.ra2.mysql.repository;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String createCustomers() {
        String sql = "INSERT INTO customers (nombre, descr, age, course, dataCreated, dataUpdated) VALUES (?,?,?,?,?,?)";
        LocalDateTime now = LocalDateTime.now();

        String[] names = {"AAA", "BBB", "CCC", "DDD", "EEE", "FFF", "GGG", "HHH", "III", "JJJ"};
        String[] description = {
            "Estudiante de matemáticas",
            "Estudiante de física",
            "Estudiante de química",
            "Estudiante de biología",
            "Estudiante de informática",
            "Estudiante de historia",
            "Estudiante de literatura",
            "Estudiante de arte",
            "Estudiante de música",
            "Estudiante de filosofía"
        };
        int[] ages = {20, 21, 22, 23, 24, 25, 26, 27, 28, 29};
        String[] courses = {"Mates", "Fisica", "Quimica", "Bio", "Mates", "Fisica", "Ctalán", "Bio", "Quimica", "Fisica"};

        for (int i = 0; i < 10; i++) {
            jdbcTemplate.update(sql, names[i], description[i], ages[i], courses[i], now, now);
        }
        return "Se han insertado correctamente 10 clientes.";
    }
    public String testConnection() {
        try {
            Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            if (result != null && result == 1) {
                return "Conectado correctamente a la base de datos dbcustomer.";
            } else {
                return "Conexión a la base de datos establecida pero prueba fallida.";
            }
        } catch (Exception e) {
            return "Error al conectar con la base de datos: " + e.getMessage();
        }
    }
}

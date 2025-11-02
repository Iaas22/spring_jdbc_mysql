package com.ra2.mysql.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ra2.mysql.model.Customer;

@Repository
public class CustomerRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

     public ResponseEntity<String> createCustomers(Customer customer) {
        String sql = "INSERT INTO customers (nombre, descr, age, course, password, dataCreated, dataUpdated) VALUES (?,?,?,?,?,?,?)";
        LocalDateTime now = LocalDateTime.now();

        // Datos base automáticos
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
        String[] courses = {"Mates", "Fisica", "Quimica", "Bio", "Mates", "Fisica", "Catalán", "Bio", "Quimica", "Fisica"};

        // Inserta 10 registros con datos generados + password del request
        for (int i = 0; i < 10; i++) {
            jdbcTemplate.update(
                sql,
                names[i],
                description[i],
                ages[i],
                courses[i],
                customer.getPassword() != null ? customer.getPassword() : "default123",
                now,
                now
            );
        }

        String message = "Se han insertado correctamente 10 customers en la base de datos.";
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

   public ResponseEntity<List<Customer>> getAllCustomers() {
    try {
        String sql = "SELECT * FROM customers";

        List<Customer> customers = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Customer c = new Customer();
            c.setId(rs.getInt("id"));
            c.setNombre(rs.getString("nombre"));
            c.setDescr(rs.getString("descr"));
            c.setAge(rs.getInt("age"));
            c.setCourse(rs.getString("course"));
            c.setPassword(rs.getString("password"));
            c.setDataCreated(rs.getTimestamp("dataCreated") != null ? rs.getTimestamp("dataCreated").toLocalDateTime() : null);
            c.setDataUpdated(rs.getTimestamp("dataUpdated") != null ? rs.getTimestamp("dataUpdated").toLocalDateTime() : null);
            return c;
        });

        if (customers.isEmpty()) {
            // Retorna null en el body, sin error
            return ResponseEntity.ok().body(null);
        }

        return ResponseEntity.ok(customers);

    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(500).body(null);
    }
}

    public ResponseEntity<Customer> getCustomerById(int id) {
    try {
        String sql = "SELECT * FROM customers WHERE id = ?";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, id);

        if (result.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }

        Map<String, Object> data = result.get(0);
        Customer customer = new Customer();

        // Conversión a int 
        customer.setId(((Number) data.get("id")).intValue());

        customer.setNombre((String) data.get("nombre"));
        customer.setDescr((String) data.get("descr"));
        customer.setAge((Integer) data.get("age"));
        customer.setCourse((String) data.get("course"));
        customer.setPassword((String) data.get("password"));

        Object created = data.get("dataCreated");
        Object updated = data.get("dataUpdated");

        if (created instanceof java.sql.Timestamp) {
            customer.setDataCreated(((java.sql.Timestamp) created).toLocalDateTime());
        }
        if (updated instanceof java.sql.Timestamp) {
            customer.setDataUpdated(((java.sql.Timestamp) updated).toLocalDateTime());
        }

        return ResponseEntity.ok(customer);

    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(500).body(null);
    }
}

public ResponseEntity<Customer> updateCustomer(int id, Customer customer) {
    try {
        String sql = """
            UPDATE customers 
            SET nombre = ?, descr = ?, age = ?, course = ?, password = ?, dataUpdated = ? 
            WHERE id = ?
        """;

        int rows = jdbcTemplate.update(sql,
                customer.getNombre(),
                customer.getDescr(),
                customer.getAge(),
                customer.getCourse(),
                customer.getPassword(), 
                new java.sql.Timestamp(System.currentTimeMillis()),
                id
        );

        if (rows == 0) {
            return ResponseEntity.status(404).body(null);
        }

        ResponseEntity<Customer> response = getCustomerById(id);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return ResponseEntity.ok(response.getBody());
        } else {
            return ResponseEntity.status(500).body(null);
        }

    } catch (Exception e) {
        System.err.println("Error actualitzant el client: " + e.getMessage());
        return ResponseEntity.status(500).body(null);
    }
}


    public Customer updateCustomerPartial(int id, Integer age) {
    String sql = "UPDATE customers SET age = ?, dataUpdated = ? WHERE id = ?";
    int rows = jdbcTemplate.update(sql, age, new java.sql.Timestamp(System.currentTimeMillis()), id);

    if (rows == 0) {
        return null;
    }

    String selectSql = "SELECT * FROM customers WHERE id = ?";
    return jdbcTemplate.queryForObject(selectSql, new BeanPropertyRowMapper<>(Customer.class), id);
}
public String deleteCustomer(int id) {
    try {
        String sql = "DELETE FROM customers WHERE id = ?";
        int rows = jdbcTemplate.update(sql, id);

        if (rows == 0) {
            return "No se encontró ningún customer con ID: " + id;
        }

        return "Customer con ID " + id + " eliminado correctamente.";

    } catch (Exception e) {
        return "Error eliminando el customer con ID " + id + ": " + e.getMessage();
    }
}

}

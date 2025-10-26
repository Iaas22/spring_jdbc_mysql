package com.ra2.mysql.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ra2.mysql.model.Customer;

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

   public String getAllCustomers() {
        try {
            String sql = "SELECT * FROM customers";
            List<Map<String, Object>> customers = jdbcTemplate.queryForList(sql);

            if (customers.isEmpty()) {
                return "No hay clientes en la base de datos.";
            }

            StringBuilder result = new StringBuilder("Lista de clientes:\n\n");
            for (Map<String, Object> customer : customers) {
                result.append("ID: ").append(customer.get("id")).append("\n");
                result.append("Nombre: ").append(customer.get("nombre")).append("\n");
                result.append("Descripción: ").append(customer.get("descr")).append("\n");
                result.append("Edad: ").append(customer.get("age")).append("\n");
                result.append("Curso: ").append(customer.get("course")).append("\n");
                result.append("Fecha creación: ").append(customer.get("dataCreated")).append("\n");
                result.append("Fecha actualización: ").append(customer.get("dataUpdated")).append("\n\n");
            }

            return result.toString();

        } catch (Exception e) {
            return "Error al obtener los clientes: " + e.getMessage();
        }
    }

    public String getCustomerById(int id) {
    try {
        String sql = "SELECT * FROM customers WHERE id = ?";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, id);

        if (result.isEmpty()) {
            return "No se encontró ningún cliente con el ID: " + id;
        }

        Map<String, Object> customer = result.get(0);

        StringBuilder sb = new StringBuilder("Datos del cliente:\n\n");
        sb.append("ID: ").append(customer.get("id")).append("\n");
        sb.append("Nombre: ").append(customer.get("nombre")).append("\n");
        sb.append("Descripción: ").append(customer.get("descr")).append("\n");
        sb.append("Edad: ").append(customer.get("age")).append("\n");
        sb.append("Curso: ").append(customer.get("course")).append("\n");
        sb.append("Fecha creación: ").append(customer.get("dataCreated")).append("\n");
        sb.append("Fecha actualización: ").append(customer.get("dataUpdated")).append("\n");

        return sb.toString();

    } catch (Exception e) {
        return "Error al obtener el cliente con ID " + id + ": " + e.getMessage();
    }
}

public String updateCustomer(int id, Customer customer) {
        try {
            String sql = "UPDATE customers SET nombre = ?, descr = ?, age = ?, course = ?, dataUpdated = ? WHERE id = ?";
            
            int rows = jdbcTemplate.update(sql,
                    customer.getNombre(),
                    customer.getDescr(),
                    customer.getAge(),
                    customer.getCourse(),
                    new java.sql.Timestamp(System.currentTimeMillis()), 
                    id
            );

            if (rows == 0) {
                return "No s'ha trobat cap customer amb ID: " + id;
            }

            return getCustomerById(id);

        } catch (Exception e) {
            return "Error actualitzant el customer amb ID " + id + ": " + e.getMessage();
        }
    }

    public String updateCustomerPartial(int id, String nombre, Integer age) {
    try {
        String sql = "UPDATE customers SET ";

        List<Object> params = new ArrayList<>();
        if (nombre != null) {
            sql += "nombre = ?, ";
            params.add(nombre);
        }
        if (age != null) {
            sql += "age = ?, ";
            params.add(age);
        }

        sql += "dataUpdated = ? WHERE id = ?";
        params.add(new java.sql.Timestamp(System.currentTimeMillis()));
        params.add(id);

        int rows = jdbcTemplate.update(sql, params.toArray());

        if (rows == 0) {
            return "No se encontró ningún customer con ID: " + id;
        }

        return getCustomerById(id);

    } catch (Exception e) {
        return "Error actualizando el customer con ID " + id + ": " + e.getMessage();
    }
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

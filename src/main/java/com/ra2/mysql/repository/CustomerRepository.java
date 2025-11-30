package com.ra2.mysql.repository;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ra2.mysql.model.Customer;

@Repository
public class CustomerRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<Customer> findAll() {
        String sql = "SELECT * FROM customers";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Customer.class));
    }

    public Customer findById(int id) {
        String sql = "SELECT * FROM customers WHERE id = ?";
        List<Customer> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Customer.class), id);
        return result.isEmpty() ? null : result.get(0);
    }

    public int updateFull(int id, Customer c) {
        String sql = """
            UPDATE customers 
            SET nombre=?, descr=?, age=?, course=?, password=?, dataUpdated=? 
            WHERE id=?
        """;
        return jdbcTemplate.update(sql,
            c.getNombre(),
            c.getDescr(),
            c.getAge(),
            c.getCourse(),
            c.getPassword(),
            LocalDateTime.now(),
            id
        );
    }

    public int updateAge(int id, int age) {
        String sql = "UPDATE customers SET age=?, dataUpdated=? WHERE id=?";
        return jdbcTemplate.update(sql, age, LocalDateTime.now(), id);
    }

    public int delete(int id) {
        String sql = "DELETE FROM customers WHERE id=?";
        return jdbcTemplate.update(sql, id);
    }
    
    public int updateImagePath(Long id, String imagePath) {
    String sql = "UPDATE customers SET image_path = ?, dataUpdated = ? WHERE id = ?";
    return jdbcTemplate.update(sql, imagePath, LocalDateTime.now(), id);
}



}

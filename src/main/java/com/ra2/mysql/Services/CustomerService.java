package com.ra2.mysql.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ra2.mysql.model.Customer;
import com.ra2.mysql.repository.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    private void validateCustomer(Customer c) {
        if (c.getNombre() == null || c.getNombre().length() < 3) {
            throw new IllegalArgumentException("El nombre debe tener al menos 3 caracteres.");
        }
    }

    private Customer ensureExists(int id) {
        Customer c = repository.findById(id);
        if (c == null) {
            throw new IllegalArgumentException("No existe ningún customer con id: " + id);
        }
        return c;
    }

    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    public Customer getCustomerById(int id) {
        return ensureExists(id);
    }

    public Customer updateCustomer(int id, Customer newData) {

        validateCustomer(newData);
        ensureExists(id);

        repository.updateFull(id, newData);
        return repository.findById(id);
    }

    public Customer updateCustomerAge(int id, int age) {

        ensureExists(id);
        repository.updateAge(id, age);
        return repository.findById(id);
    }

    public String deleteCustomer(int id) {

        ensureExists(id);
        int rows = repository.delete(id);

        if (rows == 0) {
            return "El registro ya había sido eliminado previamente.";
        }

        return "Customer eliminado correctamente.";
    }

    public ResponseEntity<String> uploadUserImage(Long userId, MultipartFile imageFile) {
        Customer customer = repository.findById(userId.intValue());
        if (customer == null) {
            return ResponseEntity.status(404)
                    .body("Usuario con id " + userId + " no encontrado.");
        }

        try {
            Path imagesDir = Paths.get("src/main/resources/private/images");
            if (!Files.exists(imagesDir)) {
                Files.createDirectories(imagesDir);
            }

            String originalFilename = imageFile.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String newFileName = "user_" + userId + extension;

            Path targetPath = imagesDir.resolve(newFileName);
            Files.copy(imageFile.getInputStream(), targetPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

            String relativePath = "private/images/" + newFileName;
            repository.updateImagePath(userId, relativePath);

            return ResponseEntity.ok("Imagen subida correctamente: " + relativePath);

        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al subir la imagen: " + e.getMessage());
        }
    }

    public int processCsvFile(MultipartFile csvFile) throws IOException {
        if (csvFile.isEmpty()) {
            throw new IllegalArgumentException("El archivo CSV está vacío.");
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(csvFile.getInputStream()));
        String line;
        int totalAdded = 0;

        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            if (fields.length < 5) {
                continue;
            }

            Customer c = new Customer();
            c.setNombre(fields[0].trim());
            c.setDescr(fields[1].trim());
            c.setAge(Integer.parseInt(fields[2].trim()));
            c.setCourse(fields[3].trim());
            c.setPassword(fields[4].trim());

            validateCustomer(c);
            repository.insert(c);
            totalAdded++;
        }

        Path csvDir = Paths.get("src/main/resources/csv_processed");
        if (!Files.exists(csvDir)) {
            Files.createDirectories(csvDir);
        }
        Path targetPath = csvDir.resolve(csvFile.getOriginalFilename());
        Files.copy(csvFile.getInputStream(), targetPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

        return totalAdded;
    }

    public int processJsonFile(MultipartFile jsonFile) throws IOException {
        if (jsonFile.isEmpty()) {
            throw new IllegalArgumentException("El archivo JSON está vacío.");
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonFile.getInputStream());
        JsonNode usersNode = root.path("data").path("users");

        if (!usersNode.isArray()) {
            throw new IllegalArgumentException("El JSON no tiene el formato esperado.");
        }

        int totalAdded = 0;
        for (JsonNode userNode : usersNode) {
            Customer c = new Customer();
            c.setNombre(userNode.path("name").asText());
            c.setDescr(userNode.path("description").asText());
            c.setCourse(userNode.path("course").asText());
            c.setPassword(userNode.path("password").asText());
            c.setAge(0);

            validateCustomer(c);
            repository.insert(c);
            totalAdded++;
        }

        Path jsonDir = Paths.get("src/main/resources/json_processed");
        if (!Files.exists(jsonDir)) {
            Files.createDirectories(jsonDir);
        }
        Path targetPath = jsonDir.resolve(jsonFile.getOriginalFilename());
        Files.copy(jsonFile.getInputStream(), targetPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

        return totalAdded;
    }

}

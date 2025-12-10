package com.sgcore.backend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgcore.backend.model.FAQ;
import com.sgcore.backend.model.Product;
import com.sgcore.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Product addProduct(Product product) {
        return repository.save(product);
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Product getProductById(String id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteProduct(String id) {
        repository.deleteById(id);
    }

    // Convert FAQ JSON to List<FAQ>
    public static List<FAQ> convertFaqJsonToList(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, new TypeReference<List<FAQ>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

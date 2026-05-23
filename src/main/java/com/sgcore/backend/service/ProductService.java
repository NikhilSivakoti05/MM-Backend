//package com.sgcore.backend.service;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sgcore.backend.model.FAQ;
//import com.sgcore.backend.model.Product;
//import com.sgcore.backend.repository.ProductRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class ProductService {
//
//    @Autowired
//    private ProductRepository repository;
//
//    public Product save(Product product) {
//        return repository.save(product);
//    }
//
//    public List<Product> getAll() {
//        return repository.findAll();
//    }
//
//    public Product getById(String id) {
//        return repository.findById(id).orElse(null);
//    }
//
//    public void delete(String id) {
//        repository.deleteById(id);
//    }
//
//    public static List<FAQ> convertFaqJsonToList(String json) {
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            return mapper.readValue(json, new TypeReference<List<FAQ>>() {});
//        } catch (Exception e) {
//            return null;
//        }
//    }
//}
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


    // =========================
    // SAVE
    // =========================

    public Product save(Product product) {

        return repository.save(product);
    }


    // =========================
    // GET ALL
    // =========================

    public List<Product> getAll() {

        return repository.findAll();
    }


    // =========================
    // GET BY ID
    // =========================

    public Product getById(String id) {

        return repository.findById(id).orElse(null);
    }


    // =========================
    // DELETE
    // =========================

    public void delete(String id) {

        repository.deleteById(id);
    }


    // =========================
    // FAQ JSON CONVERTER
    // =========================

    public static List<FAQ> convertFaqJsonToList(
            String json
    ) {

        try {

            ObjectMapper mapper =
                    new ObjectMapper();

            return mapper.readValue(
                    json,
                    new TypeReference<List<FAQ>>() {}
            );

        } catch (Exception e) {

            return null;
        }
    }
}
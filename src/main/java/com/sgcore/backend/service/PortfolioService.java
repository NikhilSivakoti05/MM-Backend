package com.sgcore.backend.service;

import com.sgcore.backend.model.Portfolio;
import com.sgcore.backend.repository.PortfolioRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired; // New Import

import java.util.List;
import java.util.Optional; // New Import for clarity, though not strictly needed here

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    // Manual Constructor Injection replacing @RequiredArgsConstructor
    // @Autowired is optional here as it's the only constructor, 
    // but is added for clarity and best practice.
    @Autowired 
    public PortfolioService(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    public Portfolio create(Portfolio p) {
        return portfolioRepository.save(p);
    }

    public List<Portfolio> findAll() {
        return portfolioRepository.findAll();
    }

    public Portfolio update(String id, Portfolio data) {
        // Use Optional<T> to handle the possibility of not finding the ID
        Optional<Portfolio> portfolioOptional = portfolioRepository.findById(id);

        return portfolioOptional.map(p -> {
            p.setTitle(data.getTitle());
            p.setDescription(data.getDescription());
            p.setCategory(data.getCategory());
            
            // Only update the image if the new data contains one
            if (data.getImageBase64() != null && !data.getImageBase64().isEmpty()) {
                p.setImageBase64(data.getImageBase64());
            }
            return portfolioRepository.save(p);
        }).orElse(null);
    }

    public void delete(String id) {
        portfolioRepository.deleteById(id);
    }
}
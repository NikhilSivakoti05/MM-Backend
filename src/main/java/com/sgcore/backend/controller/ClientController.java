package com.sgcore.backend.controller;

import com.sgcore.backend.model.Client;
import com.sgcore.backend.repository.ClientRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")

public class ClientController {

    private final ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @PostMapping
    public Client addClient(@RequestBody Client client) {
        return clientRepository.save(client);
    }

    @PutMapping("/{id}")
    public Client updateClient(@PathVariable String id, @RequestBody Client updatedClient) {
        Optional<Client> existing = clientRepository.findById(id);
        if (existing.isPresent()) {
            Client client = existing.get();
            client.setName(updatedClient.getName());
            client.setLat(updatedClient.getLat());
            client.setLng(updatedClient.getLng());
            client.setCountry(updatedClient.getCountry());
            return clientRepository.save(client);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable String id) {
        clientRepository.deleteById(id);
    }
}

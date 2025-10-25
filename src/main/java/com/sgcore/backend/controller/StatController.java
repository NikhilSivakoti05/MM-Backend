package com.sgcore.backend.controller;



import com.sgcore.backend.model.Stat;
import com.sgcore.backend.repository.StatRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stats")

public class StatController {

    private final StatRepository statRepository;

    public StatController(StatRepository statRepository) {
        this.statRepository = statRepository;
    }

    @GetMapping
    public List<Stat> getAllStats() {
        return statRepository.findAll();
    }

    @PostMapping
    public Stat addStat(@RequestBody Stat stat) {
        return statRepository.save(stat);
    }

    @PutMapping("/{id}")
    public Stat updateStat(@PathVariable String id, @RequestBody Stat updatedStat) {
        Optional<Stat> existing = statRepository.findById(id);
        if (existing.isPresent()) {
            Stat stat = existing.get();
            stat.setLabel(updatedStat.getLabel());
            stat.setValue(updatedStat.getValue());
            return statRepository.save(stat);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteStat(@PathVariable String id) {
        statRepository.deleteById(id);
    }
}

package com.sgcore.backend.service;

import com.sgcore.backend.model.AuditLog;
import com.sgcore.backend.repository.AuditLogRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuditService {

    private final AuditLogRepository repo;

    public AuditService(AuditLogRepository repo) {
        this.repo = repo;
    }

    public void log(String action, String ip) {
        AuditLog log = new AuditLog();
        log.setAction(action);
        log.setIp(ip);
        log.setTimestamp(Instant.now());
        repo.save(log);
    }
}

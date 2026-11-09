package com.example.bookmanager.service.impl;

import com.example.bookmanager.model.AuditLog;
import com.example.bookmanager.repository.AuditLogRepository;
import com.example.bookmanager.service.AuditService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AuditServiceImpl implements AuditService {

    private final AuditLogRepository auditLogRepository;

    public AuditServiceImpl(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logOperation(String message) {
        AuditLog auditLog = new AuditLog();
        auditLog.setMessage(message);
        auditLog.setCreatedAt(LocalDateTime.now());

        auditLogRepository.save(auditLog);

        System.out.println("[Audit]" + message);
    }
}

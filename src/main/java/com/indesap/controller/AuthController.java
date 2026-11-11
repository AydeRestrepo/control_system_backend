package com.indesap.controller;

import com.indesap.entity.Employee;
import com.indesap.entity.Session;
import com.indesap.entity.StatusEmployee;
import com.indesap.repository.EmployeeRepository;
import com.indesap.repository.SessionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final EmployeeRepository employeeRepository;
    private final SessionRepository sessionRepository;

    public AuthController(EmployeeRepository employeeRepository, SessionRepository sessionRepository) {
        this.employeeRepository = employeeRepository;
        this.sessionRepository = sessionRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String code = body.get("code");
        if (code == null || code.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "El código es obligatorio"));
        }

        Employee employee = employeeRepository.findByCode(code.trim())
                .orElse(null);

        if (employee == null) {
            return ResponseEntity.status(401).body(Map.of("message", "Código no encontrado"));
        }

        Session session = Session.builder()
                .employee(employee)
                .checkInTime(LocalDateTime.now())
                .status(StatusEmployee.ACTIVE)
                .build();

        sessionRepository.save(session);

        return ResponseEntity.ok(Map.of(
                "sessionId", session.getSessionId(),
                "code", employee.getCode(),
                "name", employee.getName(),
                "checkInTime", session.getCheckInTime().toString(),
                "status", session.getStatus().toString()
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Map<String, Long> body) {
        Long sessionId = body.get("sessionId");
        Session session = sessionRepository.findById(sessionId).orElse(null);
        if (session == null) {
            return ResponseEntity.status(404).body(Map.of("Information", "sesión no encontrada"));
        }
        LocalDateTime now = LocalDateTime.now();
        session.setCheckOutTime(now);
        long seconds = java.time.Duration.between(session.getCheckInTime(), now).getSeconds();
        session.setTotalSeconds(seconds);
        session.setStatus(StatusEmployee.INACTIVE);
        sessionRepository.save(session);
        return ResponseEntity.ok(Map.of("Information", "sesión cerrada", "Total de segundos", seconds));
    }
}

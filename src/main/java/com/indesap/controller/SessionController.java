package com.indesap.controller;

import com.indesap.entity.Employee;
import com.indesap.exception.EmployeeException;
import com.indesap.exception.SessionException;
import com.indesap.service.SessionService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService service;

    @PostMapping("/init")
    public ResponseEntity<?> init(@RequestBody Map<String, String> body) {
        try {
            return ResponseEntity.ok(service.initSession(body.get("code")));
        } catch (SessionException exception) {
            return ResponseEntity.badRequest().body(Map.of("Error", exception.getMessage()));
        }
    }

    @PostMapping("/finish")
    public ResponseEntity<?> finish(@RequestBody Map<String, String> body) {
        try {
            return ResponseEntity.ok(service.finallySession(body.get("code")));
        } catch (SessionException exception) {
            return ResponseEntity.badRequest().body(Map.of("Error", exception.getMessage()));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Map<String, String> body) {
        try {
            return ResponseEntity.ok(service.createEmployed(body.get("code"), body.get("name")));
        } catch (EmployeeException exception) {
            return ResponseEntity.badRequest().body(Map.of("Error", exception.getMessage()));
        }
    }
}

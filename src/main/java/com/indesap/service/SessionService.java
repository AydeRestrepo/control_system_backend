package com.indesap.service;

import com.indesap.entity.Employee;
import com.indesap.entity.Session;
import com.indesap.entity.StatusEmployee;
import com.indesap.exception.EmployeeException;
import com.indesap.exception.ErrorType;
import com.indesap.exception.SessionException;
import com.indesap.repository.EmployeeRepository;
import com.indesap.repository.SessionRepository;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final EmployeeRepository employeeRepository;
    private final SessionRepository sessionRepository;

    public Session initSession(String code) {

        Employee employee = employeeRepository.findByCode(code)
                .orElseThrow(() -> new SessionException(ErrorType.CODE_NOT_FOUND));

        if (sessionRepository.findByEmployeeAndStatus(employee, StatusEmployee.ACTIVE).isPresent())
            throw new SessionException(ErrorType.SESSION_ALREADY_ACTIVE);
        return sessionRepository.save(Session.builder()
                .employee(employee)
                .checkInTime(LocalDateTime.now())
                .status(StatusEmployee.ACTIVE)
                .build());
    }

    public Session finallySession(String code) {

        Employee employee = employeeRepository.findByCode(code)
                .orElseThrow(() -> new SessionException(ErrorType.CODE_NOT_FOUND));

        Session session = sessionRepository.findByEmployeeAndStatus(employee, StatusEmployee.ACTIVE)
                .orElseThrow(() -> new SessionException(ErrorType.WITHOUT_SESSION_ACTIVE));

        session.setCheckOutTime(LocalDateTime.now());
        session.setTotalSeconds(Duration.between(session.getCheckInTime(), session.getCheckOutTime()).getSeconds());
        session.setStatus(StatusEmployee.INACTIVE);

        return sessionRepository.save(session);
    }

    public Employee createEmployed(String code, String name) {
        if (employeeRepository.findByCode(code).isPresent()) {
            throw new EmployeeException(ErrorType.USER_EXISTING);
        }
        return employeeRepository.save(Employee.builder().code(code).name(name).build());
    }
}
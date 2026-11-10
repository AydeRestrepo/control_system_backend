package com.indesap.repository;

import com.indesap.entity.Employee;
import com.indesap.entity.Session;
import com.indesap.entity.StatusEmployee;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;



public interface SessionRepository extends JpaRepository<Session, Long> {

    Optional<Session> findByEmployeeAndStatus(Employee employee, StatusEmployee status);

}

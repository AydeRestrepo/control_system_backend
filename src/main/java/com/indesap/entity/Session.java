package com.indesap.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long sessionId;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private Long totalSeconds;

    @Enumerated(EnumType.STRING)
    private StatusEmployee status;

    public String getTotalSecondsFormatter() {
        if (totalSeconds == null) return "00:00:00";
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
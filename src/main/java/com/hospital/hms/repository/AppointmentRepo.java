package com.hospital.hms.repository;

import com.hospital.hms.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepo extends JpaRepository<Appointment,Long> {
    List<Appointment> findByEmployeeId(Long empId);
    List<Appointment> findByUserId(Long userId);
}

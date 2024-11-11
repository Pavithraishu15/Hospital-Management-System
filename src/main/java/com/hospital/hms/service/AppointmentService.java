package com.hospital.hms.service;

import com.hospital.hms.model.Appointment;
import com.hospital.hms.model.User;
import com.hospital.hms.repository.AppointmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    @Autowired
    AppointmentRepo appointmentRepo;

    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepo.save(appointment);
    }
    public Optional<Appointment> findById(Long id) {
        return appointmentRepo.findById(id); // Assuming you're using JPA
    }
    public Appointment save(Appointment appointment) {
        return appointmentRepo.save(appointment); // Save the appointment to the database
    }
    public List<Appointment> findByEmployeeId(Long id) {
        return appointmentRepo.findByEmployeeId(id);
    }
    public List<Appointment> getUser(){
        return appointmentRepo.findAll();
    }

    public List<Appointment> findByUserId(Long id) {
        return appointmentRepo.findByUserId(id);
    }
    public void deleteAppointment(Long id){
        appointmentRepo.deleteById(id);
    }
}


package com.hospital.hms.controller;

import com.hospital.hms.model.Appointment;
import com.hospital.hms.model.Employee;
import com.hospital.hms.model.User;
import com.hospital.hms.service.AppointmentService;
import com.hospital.hms.service.UserService;
import com.hospital.hms.service.Userser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin

public class AuthController {
    @Autowired
    UserService userService;
    @Autowired
    Userser userser;
    @Autowired
    AppointmentService appointmentService;

    @PostMapping("/doctor")
    public Appointment createAppointment(@RequestBody Appointment appointment) {
        // Retrieve Employee and User objects based on IDs
        Employee employee = userser.getEmployeeById(appointment.getEmployee().getId());
        User user = userService.getUserById(appointment.getUser().getId());

        // Set the retrieved objects back into the appointment
        appointment.setEmployee(employee);
        appointment.setUser(user);
        return appointmentService.createAppointment(appointment);
    }
    @GetMapping("/doctor")
    public List<Appointment> readAppointment(){
        return appointmentService.getUser();
    }
@DeleteMapping("/doctor/{id}")
public void deleteAppointment(@PathVariable(value = "id") Long id){
        appointmentService.deleteAppointment(id);
}

    @GetMapping("/doctor/{id}")
    public List<Appointment> getAppointmentsByDoctor(@PathVariable Long id) {
        return appointmentService.findByEmployeeId(id);
    }


    @GetMapping("/patient/{id}")
    public ResponseEntity<List<Appointment>>getAppointmentsByPatient(@PathVariable Long id) {
        List<Appointment> appointments = appointmentService.findByUserId(id);

        return ResponseEntity.ok(appointments);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.register(user));
    }
    @GetMapping("/register")
    public List<User> readUser(){
        return userService.getUser();
    }
    @GetMapping("/register/{id}")
    public User getUser(@PathVariable(value="id")Long id)
    {
        return userService.getUserById(id);
    }
@DeleteMapping("/register/{id}")
public void deleteUser(@PathVariable(value = "id")Long id){
        userService.deleteUser(id);
}
    @PutMapping("/register/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);

        if (updatedUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // User not found
        }

        return ResponseEntity.ok(updatedUser); // Return updated user with 200 OK
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> login(@RequestBody Map<String, String> credentials) {
        if (credentials == null || !credentials.containsKey("username") || !credentials.containsKey("password")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message","Username and password are required"));
        }

        String username = credentials.get("username");
        String password = credentials.get("password");

        try {
            Optional<User> user = userService.login(username, password);
            if (user.isPresent()) {
                Long userId=user.get().getId();
                return ResponseEntity.ok(Map.of("message", "Login successful", "userId", userId));

            }
            else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid credentials"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "An error occurred during login"));

        }


    }


    }


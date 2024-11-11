package com.hospital.hms.controller;

import com.hospital.hms.model.Appointment;
import com.hospital.hms.model.Employee;
import com.hospital.hms.service.AppointmentService;
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
public class EmployeeLoginController {
@Autowired
    Userser userser;
@Autowired
    AppointmentService appointmentService;
    @GetMapping("/appointments")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctorId(@RequestParam Long empId) {
        List<Appointment> appointments = appointmentService.findByEmployeeId(empId);
        return ResponseEntity.ok(appointments);
    }
    @PutMapping("/appointments/{id}")
    public ResponseEntity<Appointment> updateAppointmentStatus(@PathVariable Long id, @RequestBody Map<String, String> updates) {
        Optional<Appointment> optionalAppointment = appointmentService.findById(id);

        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            String newStatus = updates.get("status");

            if (newStatus == null || newStatus.isEmpty()) {
                return ResponseEntity.badRequest().body(null); // Or return a specific error message
            }

            appointment.setStatus(newStatus);
            appointmentService.save(appointment); // Save the updated appointment
            return ResponseEntity.ok(appointment);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @PostMapping("/registerDoctor")
    public ResponseEntity<Employee> register(@RequestBody Employee employee) {
        return ResponseEntity.ok(userser.register(employee));
    }
    @RequestMapping(value="/employee",method= RequestMethod.GET)
    public List<Employee> readEmployee(){
        return userser.getEmployees();
    }
    @GetMapping("/employee/{emp_id}")
    public Employee getEmployee(@PathVariable (value = "emp_id") Long empId)
    {
        return userser.getEmployeeById(empId);
    }

    @RequestMapping(value="/employee/{emp_id}",method = RequestMethod.PUT)
    public Employee readEmployee(@PathVariable(value="emp_id")Long id,@RequestBody Employee empdetails)
    {
        return userser.updateEmployee(id, empdetails);
    }
    @RequestMapping(value="/employee/{emp_id}",method = RequestMethod.DELETE)
    public void deleteEmployee(@PathVariable(value="emp_id")Long id)
    {
        userser.deleteEmployee(id);
    }
    @PostMapping("/loginDoctor")
    public ResponseEntity<Map<String, Object>> loginDoctor(@RequestBody Map<String, String> credentials) {
        if (credentials == null || !credentials.containsKey("userName") || !credentials.containsKey("password")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "UserName and password are required"));
        }

        String userName = credentials.get("userName");
        String password = credentials.get("password");

        try {
            Optional<Employee> employee = userser.loginDoctor(userName, password);
            if (employee.isPresent()) {
                Long empId = employee.get().getId();
                return ResponseEntity.ok(Map.of("message", "Login successful", "userId", empId));

            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid credentials"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "An error occurred during login"));

        }
    }
}

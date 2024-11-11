package com.hospital.hms.service;





import com.hospital.hms.model.Employee;
import com.hospital.hms.repository.Userrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.List;
import java.util.Optional;


@Service
public class Userser {
    @Autowired
    Userrepo userrepo;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    public Employee register(Employee employee) {
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));

        return userrepo.save(employee);
    }

    // READ
    public List<Employee> getEmployees() {
        return userrepo.findAll();
    }
    public Employee getEmployeeById(Long empId)
    {
        return userrepo.findById(empId).get();
    }

    // DELETE
    public void deleteEmployee(Long empId) {
        userrepo.deleteById(empId);
    }
    public Employee updateEmployee(Long empId, Employee employeeDetails) {
        Employee emp = userrepo.findById(empId).get();
        emp.setFirstName(employeeDetails.getFirstName());
        emp.setLastName(employeeDetails.getLastName());
        emp.setSpecialist(employeeDetails.getSpecialist());
        emp.setDoctorType(employeeDetails.getDoctorType());
        emp.setAge(employeeDetails.getAge());
        emp.setExperience(employeeDetails.getExperience());
        emp.setPassword(employeeDetails.getPassword());
        emp.setUserName(employeeDetails.getUserName());


        return userrepo.save(emp);
    }
    public Optional<Employee> loginDoctor(String userName, String password) {
        if (userName == null || password == null) {
            return Optional.empty(); // Return empty if username or password is null
        }
try {
    return userrepo.findByUserName(userName)
            .filter(user -> {
                boolean matches = passwordEncoder.matches(password, user.getPassword());
                System.out.println("Password match: " + matches);
                return matches;
            });
}
catch (Exception e){
   System.out.println("Error" + e);
   return Optional.empty();

}
    }
}


package com.example.JpaNewAssignment.Controller;

import com.example.JpaNewAssignment.Exceptions.InvalidInput;
import com.example.JpaNewAssignment.Model.Address;
import com.example.JpaNewAssignment.Model.Employee;
import com.example.JpaNewAssignment.ServiceInterface.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private ServiceInterface employeeService;
    @PostMapping("/save")
    public ResponseEntity<String> saveEmployee(@RequestBody Employee employee) {
        if(employee.getName()==null || employee.getAddress()==null){
            throw new InvalidInput("Invalid input");

        }
        try {
            employeeService.saveEmployee(employee);
            return new ResponseEntity<String>("Employee saved successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Employee>> getAllEmployee(){
        return new ResponseEntity<List<Employee>>(employeeService.getAllEmployees(),HttpStatus.OK);
    }



    @GetMapping("/getById/{id}")
    public ResponseEntity<Employee> getByID(@PathVariable("id") int empId){
        return new ResponseEntity<Employee>(employeeService.getEmployeeById(empId), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee, @PathVariable int id){
        return new ResponseEntity<Employee>(employeeService.updateEmployee(id,employee), HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") int empId){
        employeeService.deleteEmployee(empId);

            return new ResponseEntity<String>("Employee deleted succsessfully!", HttpStatus.OK);


    }


}


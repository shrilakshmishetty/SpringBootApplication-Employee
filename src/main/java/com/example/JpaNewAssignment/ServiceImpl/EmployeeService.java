package com.example.JpaNewAssignment.ServiceImpl;

import com.example.JpaNewAssignment.Exceptions.EmployeeNotFound;
import com.example.JpaNewAssignment.Exceptions.InvalidInput;
import com.example.JpaNewAssignment.Model.Address;
import com.example.JpaNewAssignment.Model.Employee;
import com.example.JpaNewAssignment.Repository.AddressRepository;
import com.example.JpaNewAssignment.Repository.EmployeeRepository;
import com.example.JpaNewAssignment.ServiceInterface.ServiceInterface;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import java.util.List;
@Service
@Transactional(propagation= Propagation.REQUIRED)
public class EmployeeService implements ServiceInterface {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
   private AddressService addressService;


    @Override
    public void saveEmployee(Employee employee) {



            try {
                employeeRepository.save(employee);


                if (employee.getAddress() != null) {
                    addressService.saveAddress(employee.getAddress());

                }
            } catch (Exception e) {
                throw new RuntimeException("Error saving employee: " + e.getMessage(), e);
            }


        //throw new RuntimeException("its not working");

    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(int id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public Employee updateEmployee(int id, Employee employee) {
        if (!employeeRepository.existsById(id)) {
            return null;
        }
        employee.setId(id);
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(int id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFound("Employee not found"));
        employeeRepository.delete(employee);

    }
}


package com.example.JpaNewAssignment.ServiceInterface;

import com.example.JpaNewAssignment.Model.Address;
import com.example.JpaNewAssignment.Model.Employee;

import java.util.List;

public interface ServiceInterface {
    void saveEmployee(Employee employee);
    List<Employee> getAllEmployees();
    Employee getEmployeeById(int id);
    Employee updateEmployee(int id, Employee employee);
    void deleteEmployee(int id);
}

package com.example.JpaNewAssignment.EmployeeServiceTest;
import com.example.JpaNewAssignment.Model.Address;
import com.example.JpaNewAssignment.Model.Employee;
import com.example.JpaNewAssignment.Repository.EmployeeRepository;
import com.example.JpaNewAssignment.ServiceImpl.AddressService;
import com.example.JpaNewAssignment.ServiceImpl.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTestClass {
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private AddressService addressService;
    @InjectMocks
    private EmployeeService employeeService;
    @Test
    public void saveEmployeeTest(){
        Address address=new Address();
        address.setAddressName("elm street");
        Employee employee=new Employee();
        employee.setName("anika");
        employee.setAddress(address);
        employeeService.saveEmployee(employee);
        Mockito.verify(employeeRepository,Mockito.times(1)).save(employee);
        Mockito.verify(addressService,Mockito.times(1)).saveAddress(address);


    }
    @Test
    public void getAllEmployeesTest(){
        List<Employee> list=new ArrayList<>();
        Address address=new Address();
        address.setAddressName("kundapura");
        Employee employee=new Employee(1,"anika",address);
        list.add(employee);
        when(employeeRepository.findAll()).thenReturn(list);
        List<Employee> resultList=employeeService.getAllEmployees();
        assertEquals(list.size(), resultList.size());
        assertEquals(list.get(0).getName(), resultList.get(0).getName());


    }
    @Test
    public void getEmployeeIdTest(){
       int id=1;
        Address address=new Address();
        address.setAddressName("kundapura");
        Employee employee=new Employee(id,"anika",address);
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
        Employee resultEmployee = employeeService.getEmployeeById(id);
        assertEquals(employee.getName(), resultEmployee.getName());
        assertEquals(employee.getAddress().getAddressName(), resultEmployee.getAddress().getAddressName());


    }
    @Test
    public void deleteEmployeeTest(){
        int id = 1;
        Address address = new Address();
        address.setAddressName("kundapura");
        Employee employee = new Employee(id, "anika", address);

        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepository).delete(employee);
        employeeService.deleteEmployee(id);
        Mockito.verify(employeeRepository,Mockito.times(1)).findById(id);
        Mockito.verify(employeeRepository,Mockito.times(1)).delete(employee);


    }





}

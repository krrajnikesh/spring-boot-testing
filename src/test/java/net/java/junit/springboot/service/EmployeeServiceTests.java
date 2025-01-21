package net.java.junit.springboot.service;

import net.java.junit.springboot.exception.ResourceNotFoundException;
import net.java.junit.springboot.model.Employee;
import net.java.junit.springboot.repository.EmployeeRepository;
import net.java.junit.springboot.service.impl.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    void setUp(){
//        employeeRepository = Mockito.mock(EmployeeRepository.class);
//        employeeService = new EmployeeServiceImpl(employeeRepository);
         employee = Employee.builder()
                .firstName("Ram")
                .id(1L)
                .lastName("Kumar")
                .email("ram@gmail.com")
                .build();
    }

    //JUnit test for saveEmployee method
    @DisplayName("JUnit test for saveEmployee method")
    @Test
    void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject(){
        //given - precondition or setup

        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());

        given(employeeRepository.save(employee)).willReturn(employee);

        //when - action or the behaviour that are going to be tested
        Employee savedEmployee = employeeService.saveEmployee(employee);

        //then - verify the output
        Assertions.assertThat(savedEmployee).isNotNull();

    }

    //JUnit test for saveEmployee method
    @DisplayName("JUnit test for saveEmployee method which throws exception")
    @Test
    void givenExistingEmail_whenSaveEmployee_thenThrowsException(){
        //given - precondition or setup

        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));

        //when - action or the behaviour that are going to be tested
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, ()->{
            employeeService.saveEmployee(employee);
        });

        //then
        verify(employeeRepository, never()).save(any(Employee.class));

    }

    //JUnit test for get All employee method
    @DisplayName("JUnit test for get All employee method")
    @Test
    void givenEmployeeList_whenAllEmployees_thenReturnAllEmployee(){

        Employee employee1 = Employee.builder()
                .firstName("Ramesh")
                .id(2L)
                .lastName("Kumar")
                .email("ramesh@gmail.com")
                .build();
        //given - precondition or setup
        given(employeeRepository.findAll()).willReturn(List.of(employee, employee1));

        //when - action or the behaviour that are going to be tested
        List<Employee> employeeList = employeeService.getAllEmployee();

        //then - verify the output
        Assertions.assertThat(employeeList).isNotNull();
        Assertions.assertThat(employeeList.size()).isEqualTo(2);

    }

    //JUnit test for get All employee method
    @DisplayName("JUnit test for get All employee method (negative scenario)")
    @Test
    void givenEmptyEmployeeList_whenAllEmployees_thenReturnEmptyEmployeeList(){

        Employee employee1 = Employee.builder()
                .firstName("Ramesh")
                .id(2L)
                .lastName("Kumar")
                .email("ramesh@gmail.com")
                .build();
        //given - precondition or setup
        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        //when - action or the behaviour that are going to be tested
        List<Employee> employeeList = employeeService.getAllEmployee();

        //then - verify the output
        Assertions.assertThat(employeeList).isEmpty();
        Assertions.assertThat(employeeList.size()).isEqualTo(0);

    }

    //JUnit test for get employee by id method
    @DisplayName("JUnit test for get employee by id method")
    @Test
    void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject(){
        //given - precondition or setup
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        //when - action or the behaviour that are going to be tested
        Employee savedEmployee = employeeService.getEmployeeById(employee.getId()).get();

        //then - verify the output
        Assertions.assertThat(savedEmployee).isNotNull();

    }

    //JUnit test for update employee method
    @DisplayName("JUnit test for update employee method")
    @Test
    void givenEmployeeObject_whenUpdateEmployee_thenReturnEmployeeObject(){
        //given - precondition or setup
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("ramesh@gmail.com");
        employee.setFirstName("Ramesh");
        //when - action or the behaviour that are going to be tested
        Employee updatedEmployee = employeeService.updateEmployee(employee);

        //then - verify the output
        Assertions.assertThat(updatedEmployee.getEmail()).isEqualTo("ramesh@gmail.com");
        Assertions.assertThat(updatedEmployee.getFirstName()).isEqualTo("Ramesh");

    }

}

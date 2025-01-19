package net.java.junit.springboot.service;

import net.java.junit.springboot.model.Employee;
import net.java.junit.springboot.repository.EmployeeRepository;
import net.java.junit.springboot.service.impl.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.util.Optional;

public class EmployeeServiceTests {

    private EmployeeRepository employeeRepository;
    private EmployeeService employeeService;

    @BeforeEach
    void setUp(){
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        employeeService = new EmployeeServiceImpl(employeeRepository);
    }

    //JUnit test for saveEmployee method
    @DisplayName("JUnit test for saveEmployee method")
    @Test
    void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject(){
        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Ram")
                .id(1L)
                .lastName("Kumar")
                .email("ram@gmail.com")
                .build();

        BDDMockito.given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());

        BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);

        //when - action or the behaviour that are going to be tested
        Employee savedEmployee = employeeService.saveEmployee(employee);

        //then - verify the output
        Assertions.assertThat(savedEmployee).isNotNull();

    }

}

package net.java.junit.springboot.repository;

import net.java.junit.springboot.model.Employee;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    //JUnit test for save employee operation
    @DisplayName("JUnit test for save employee operation")
    @Test
    void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){
        //given - precondition or setup

        Employee employee = Employee.builder()
                .firstName("Ram")
                .lastName("Kumar")
                .email("ram@gmail.com")
                .build();

        //when - action or the behaviour that are going to be tested

        Employee savedEmployee = employeeRepository.save(employee);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);

    }

    //JUnit test for get all employees operation
    @DisplayName("JUnit test for get all employees operation")
    @Test
    void givenEmployeeList_whenFindAll_thenEmployeesList(){
        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Ram")
                .lastName("Kumar")
                .email("ram@gmail.com")
                .build();
        Employee employee1 = Employee.builder()
                .firstName("Shayam")
                .lastName("Kumar")
                .email("ram@gmail.com")
                .build();

        employeeRepository.save(employee);
        employeeRepository.save(employee1);

        //when - action or the behaviour that are going to be tested
        List<Employee> employeeList = employeeRepository.findAll();

        //then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);

    }

    //JUnit test for get employee by id operation
    @DisplayName("JUnit test for get employee by id operation")
    @Test
    void givenEmployeeObject_whenFindById_thenReturnEmployeeObject(){
        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Ram")
                .lastName("Kumar")
                .email("ram@gmail.com")
                .build();
        employeeRepository.save(employee);

        //when - action or the behaviour that are going to be tested
        Employee employeeDB =  employeeRepository.findById(employee.getId()).get();

        //then - verify the output
        assertThat(employeeDB).isNotNull();

    }

}

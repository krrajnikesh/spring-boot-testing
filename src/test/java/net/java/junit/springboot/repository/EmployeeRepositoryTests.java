package net.java.junit.springboot.repository;

import net.java.junit.springboot.model.Employee;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
}

package net.java.junit.springboot.repository;

import net.java.junit.springboot.model.Employee;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    void setUp(){
        employee = Employee.builder()
                .firstName("Ram")
                .lastName("Kumar")
                .email("ram@gmail.com")
                .build();
    }

    //JUnit test for save employee operation
    @DisplayName("JUnit test for save employee operation")
    @Test
    void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){
        //given - precondition or setup


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

        employeeRepository.save(employee);

        //when - action or the behaviour that are going to be tested
        Employee employeeDB =  employeeRepository.findById(employee.getId()).get();

        //then - verify the output
        assertThat(employeeDB).isNotNull();

    }

    //JUnit test for get employee by email operation
    @DisplayName("JUnit test for get employee by email operation")
    @Test
    void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject(){
        //given - precondition or setup

        employeeRepository.save(employee);

        //when - action or the behaviour that are going to be tested
        Employee employeeDB = employeeRepository.findByEmail(employee.getEmail()).get();

        //then - verify the output
        assertThat(employeeDB).isNotNull();

    }

    //JUnit test for update employee operation
    @DisplayName("JUnit test for update employee operation")
    @Test
    void givenEmployeeObject_whenUpdateEmployeeObject_thenUpdatedEmployeeObject(){
        //given - precondition or setup

        employeeRepository.save(employee);

        //when - action or the behaviour that are going to be tested
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();
        employeeDB.setEmail("ramesh@gmail.com");
        employeeDB.setFirstName("Ramesh");
        Employee updatedEmployee = employeeRepository.save(employeeDB);

        //then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("ramesh@gmail.com");
        assertThat(updatedEmployee.getFirstName())
                .isEqualTo("Ramesh");
    }

    //JUnit test for delete employee operation
    @DisplayName("JUnit test for delete employee operation")
    @Test
    void givenEmployeeObject_whenDelete_thenRemoveEmployee(){
        //given - precondition or setup

        employeeRepository.save(employee);
        //when - action or the behaviour that are going to be tested
        employeeRepository.delete(employee);
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

        //then - verify the output
        assertThat(employeeOptional).isEmpty();

    }

    //JUnit test for custom query using JPQL with index
    @DisplayName("JUnit test for custom query using JPQL with index")
    @Test
    void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployeeObject(){
        //given - precondition or setup

        employeeRepository.save(employee);
        String firstName = "Ram";
        String lastName = "Kumar";

        //when - action or the behaviour that are going to be tested
        Employee selectedEmployee = employeeRepository.findByJPQL(firstName, lastName);

        //then - verify the output
        assertThat(selectedEmployee).isNotNull();

    }

    //JUnit test for custom query using JPQL with named params
    @DisplayName("JUnit test for custom query using JPQL with named params")
    @Test
    void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject(){
        //given - precondition or setup

        employeeRepository.save(employee);
        String firstName = "Ram";
        String lastName = "Kumar";

        //when - action or the behaviour that are going to be tested
        Employee selectedEmployee = employeeRepository.findByNamedParams(firstName, lastName);

        //then - verify the output
        assertThat(selectedEmployee).isNotNull();

    }

    //JUnit test for custom query using native sql with indexed
    @DisplayName("JUnit test for custom query using native sql with indexed")
    @Test
    void givenFirstNameAndLastName_whenFindByNativeSQLIndexedParams_thenReturnEmployeeObject(){
        //given - precondition or setup

        employeeRepository.save(employee);
        String firstName = "Ram";
        String lastName = "Kumar";

        //when - action or the behaviour that are going to be tested
        Employee selectedEmployee = employeeRepository.findByNativeSQL(firstName, lastName);

        //then - verify the output
        assertThat(selectedEmployee).isNotNull();

    }

    //JUnit test for custom query using native sql with named params
    @DisplayName("JUnit test for custom query using native sql with named params")
    @Test
    void givenFirstNameAndLastName_whenFindByNativeSQLNamedParams_thenReturnEmployeeObject(){
        //given - precondition or setup

        employeeRepository.save(employee);
        String firstName = "Ram";
        String lastName = "Kumar";

        //when - action or the behaviour that are going to be tested
        Employee selectedEmployee = employeeRepository.findByNativeSQL(firstName, lastName);

        //then - verify the output
        assertThat(selectedEmployee).isNotNull();

    }

}

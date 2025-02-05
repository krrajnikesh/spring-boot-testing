package net.java.junit.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.java.junit.springboot.model.Employee;
import net.java.junit.springboot.service.EmployeeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
        //given
        Employee employee = Employee.builder()
                .firstName("Ram")
                .lastName("Kumar")
                .email("ram@gmail.com")
                .build();

        given(employeeService.saveEmployee(ArgumentMatchers.any(Employee.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        //when
        ResultActions response = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        //then
        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName",
                        is(employee.getFirstName())));
    }

    //JUnit test for get all employees rest api
    @DisplayName("JUnit test for get all employees rest api")
    @Test
    void givenListOfEmployees_whenGetAllEmployees_thenEmployeesList() throws Exception {
        //given - precondition or setup
        List<Employee> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(Employee.builder().firstName("Ram").lastName("Kumar").email("ram@gmail.com").build());
        listOfEmployees.add(Employee.builder().firstName("Ramesh").lastName("Kumar").email("ramesh@gmail.com").build());

        given(employeeService.getAllEmployee()).willReturn(listOfEmployees);

        //when - action or the behaviour that are going to be tested
        ResultActions response = mockMvc.perform(get("/api/employees"));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(2)));

    }

    //JUnit test for get employee by id rest api positive case
    @DisplayName("JUnit test for get employee by id rest api positive case")
    @Test
    void givenEmployeeId_whenGetEmployeeId_thenEmployeeObject() throws Exception {
        //given - precondition or setup
        long employeeId = 1L;
        Employee employee = Employee.builder().firstName("Ram").lastName("Kumar").email("ram@gmail.com").build();

        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));

        //when - action or the behaviour that are going to be tested
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is("Ram")));

    }

    //JUnit test for get employee by id rest api negative case
    @DisplayName("JUnit test for get employee by id rest api negative case")
    @Test
    void givenEmployeeId_whenGetEmployeeId_thenReturnEmpty() throws Exception {
        //given - precondition or setup
        long employeeId = 2L;
        Employee employee = Employee.builder().firstName("Ram").lastName("Kumar").email("ram@gmail.com").build();

        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());

        //when - action or the behaviour that are going to be tested
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));

        //then - verify the output
        response.andExpect(status().isNotFound());

    }

    //JUnit test for update employee rest api for positive scenario
    @DisplayName("JUnit test for update employee rest api for positive scenario")
    @Test
    void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdatedEmployee() throws Exception {
        //given - precondition or setup
        long employeeId = 1L;
        Employee savedEmployee = Employee.builder().firstName("Ram").lastName("Kumar").email("ram@gmail.com").build();

        Employee updatedEmployee = Employee.builder().firstName("Ramesh").lastName("Kumar").email("ramesh@gmail.com").build();

        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(savedEmployee));
        given(employeeService.updateEmployee(ArgumentMatchers.any(Employee.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        //when - action or the behaviour that are going to be tested
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is("Ramesh")))
                .andExpect(jsonPath("$.lastName", is("Kumar")))
                .andExpect(jsonPath("$.email", is("ramesh@gmail.com")));

    }

    //JUnit test for update employee rest api for negative scenario
    @DisplayName("JUnit test for update employee rest api for negative scenario")
    @Test
    void givenUpdatedEmployee_whenUpdateEmployee_thenReturnEmpty() throws Exception {
        //given - precondition or setup
        long employeeId = 3L;
        Employee savedEmployee = Employee.builder().firstName("Ram").lastName("Kumar").email("ram@gmail.com").build();

        Employee updatedEmployee = Employee.builder().firstName("Ramesh").lastName("Kumar").email("ramesh@gmail.com").build();

        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());
        given(employeeService.updateEmployee(ArgumentMatchers.any(Employee.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        //when - action or the behaviour that are going to be tested
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        //then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());

    }
}

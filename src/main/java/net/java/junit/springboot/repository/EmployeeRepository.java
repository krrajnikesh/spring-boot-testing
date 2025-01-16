package net.java.junit.springboot.repository;

import net.java.junit.springboot.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    //Define custom query using JPQL with index parameter
    @Query("select e from Employee e where e.firstName = ?1 and e.lastName = ?2")
    Employee findByJPQL(String firstName, String lastName);

    //Define custom query using JPQL with named parameter
    @Query("select e from Employee e where e.firstName =:firstName and e.lastName =:lastName")
    Employee findByNamedParams(@Param("firstName") String firstName, @Param("lastName") String lastName);

    //Define custom query using native sql query with indexed params
    @Query(value = "select * from employees e where e.first_name =?1 and e.last_name = ?2", nativeQuery = true)
    Employee findByNativeSQL(String firstName, String lastName);
}

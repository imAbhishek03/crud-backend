package me.crud_backend.repository;

import java.util.Optional;

import me.crud_backend.pojo.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	
//	Optional<Employee> findByEmpId(String empId);
	
//	OR
	
	@Query("SELECT e FROM Employee e WHERE e.empId = :empId")
    Optional<Employee> findByEmpId(@Param("empId") String empId);
}

package me.crud_backend.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import me.crud_backend.dto.EmployeeDetailsDTO;
import me.crud_backend.pojo.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {

//	Optional<Employee> findByEmpId(String empId);

//OR

    @Query("SELECT e FROM Employee e WHERE e.empId = :empId")
    Optional<Employee> findByEmpId(@Param("empId") String empId);

    //fetching all employee record  with department name

    @Query("SELECT new me.crud_backend.dto.EmployeeDetailsDTO(e.id, e.empId, e.name, e.email, e.phone, d.departmentName)" +
            " FROM Employee e JOIN e.department d" +
            " ORDER BY e.id ASC")
    List<EmployeeDetailsDTO> findAllEmployeeDetails();


    // fetching employee record with department name

    @Query("SELECT new me.crud_backend.dto.EmployeeDetailsDTO(e.id, e.empId, e.name, e.email, e.phone, d.departmentName)" +
            " FROM Employee e JOIN e.department d WHERE e.id = :id")
    EmployeeDetailsDTO findEmployeeWithDepartment(@Param("id") Long id);

    @Query("SELECT new me.crud_backend.dto.EmployeeDetailsDTO(e.id, e.empId, e.name, e.email, e.phone, d.departmentName)" +
            " FROM Employee e JOIN e.department d WHERE e.empId = :id")
    EmployeeDetailsDTO findEmployeeWithDepartment2(@Param("id") String id);


    @Modifying
    @Transactional
    @Query("DELETE FROM Employee e WHERE e.id = :id")
    void deleteEmployeeById(@Param("id") Long id);

}


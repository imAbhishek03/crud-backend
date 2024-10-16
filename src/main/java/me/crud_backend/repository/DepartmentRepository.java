package me.crud_backend.repository;

import me.crud_backend.dto.DepartmentDTO;
import me.crud_backend.pojo.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByDepartmentName(String departmentName);

    @Query("SELECT new me.crud_backend.dto.DepartmentDTO(d.id, d.departmentName) FROM Department d")
    List<DepartmentDTO> findAllDepartments();
}

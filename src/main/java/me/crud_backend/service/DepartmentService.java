package me.crud_backend.service;

import me.crud_backend.dto.DepartmentDTO;
import me.crud_backend.pojo.Department;

import java.util.List;

public interface DepartmentService {
    boolean addDepartment(Department request);

    List<DepartmentDTO> getDepartmentList();
}

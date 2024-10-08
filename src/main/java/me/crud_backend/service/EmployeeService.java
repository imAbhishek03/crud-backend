package me.crud_backend.service;

import me.crud_backend.dto.EmployeeDetailsDTO;
import me.crud_backend.pojo.Employee;

import java.util.List;


public interface EmployeeService {

	boolean addEmployee(EmployeeDetailsDTO employee);

	Employee getEmployee(Long id);

	EmployeeDetailsDTO getEmployeeByEmpId(String empId);

	EmployeeDetailsDTO getEmployeeWithDepartment(Long id);

	boolean deleteEmployee(Long id);

	boolean updateEmployee(Long id, EmployeeDetailsDTO request);

	List<EmployeeDetailsDTO> getAllEmployees();

}

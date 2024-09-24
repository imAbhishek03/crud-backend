package me.crud_backend.service;

import me.crud_backend.pojo.Employee;

import java.util.List;


public interface EmployeeService {

	Employee addEmployee(Employee employee);

	Employee getEmployee(Long id);

	Employee getEmployeeByEmpId(String empId);

	boolean deleteEmployee(Long id);

	Employee updateEmployee(Long id, Employee request);

	List<Employee> getAllEmployees();

}

package me.crud_backend.serviceImpl;

import java.util.List;

import me.crud_backend.pojo.Employee;
import me.crud_backend.repository.EmployeeRepository;
import me.crud_backend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	EmployeeRepository empRepository;
	
	@Override
	public Employee addEmployee(Employee employee) {
		return empRepository.save(employee);
	}
	
	@Override
	public Employee getEmployee(Long id) {
		return empRepository.findById(id).orElse(null);
	}

	@Override
	public List<Employee> getAllEmployees(){
		return empRepository.findAll();
	}
	
	@Override
	public Employee getEmployeeByEmpId(String empId) {
		return empRepository.findByEmpId(empId).orElse(null);
	}
	
	@Override
	public boolean deleteEmployee(Long id) {
		
		if(empRepository.existsById(id)) {
			empRepository.deleteById(id);
			return true;
		}
		return false;
	}
	
	@Override
	public Employee updateEmployee(Long id, Employee request) {
		
		Employee existingEmployee = empRepository.findById(id).orElse(null);
		
		if(existingEmployee != null) {
			existingEmployee.setAddress(request.getAddress());
			
			return empRepository.save(existingEmployee);
		}
		return null;
	}
}

package me.crud_backend.controller;

import java.util.List;

import me.crud_backend.pojo.Employee;
import me.crud_backend.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/crud-emp")
@CrossOrigin("http://localhost:3000")
public class CrudController {
	
	@Autowired
	EmployeeService empService;
	
	// Create logger instance
    private static final Logger logger = LoggerFactory.getLogger(CrudController.class);
	
	@PostMapping("/add")
	public ResponseEntity<Employee> addEmployee(@RequestBody Employee request) {
		
		logger.info("add employee controller started ------- ");
		
		Employee response = empService.addEmployee(request);
		System.out.println("Employee details ::::: " + response);
		
		logger.info("Record saved successfully!!!");
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<Employee> getEmployee(@PathVariable Long id){
		
		logger.info("get Employee by Id controller started ---- ");
		
		Employee response = empService.getEmployee(id);
		
		logger.info("Record fetched successfully ::: " + response);
		
		return ResponseEntity.status(HttpStatus.FOUND).body(response);
	}
	
	@GetMapping("/employees")
	public ResponseEntity<List<Employee>> getEmployeeList(){
		
		logger.info("employees controller started ----");
		
		List<Employee> response = empService.getAllEmployees();
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/getByEmpId/{empId}")
	public ResponseEntity<Employee> getEmployeeByEmpId(@PathVariable String empId){
		
		logger.info("getByEmpId controller started ---------- ");
		
		Employee response = empService.getEmployeeByEmpId(empId);
		
		System.out.println("Employee details :::: " + response);
		
		return ResponseEntity.status(HttpStatus.FOUND).body(response);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable Long id){
		logger.info("delete employee controller started -------- ");
		
		if(empService.deleteEmployee(id)) {
			logger.info("Record deleted successfully!!!");
			return ResponseEntity.status(HttpStatus.OK).body("Record deleted Successfully!!!");
		} else {
			logger.error("Something went wrong!!!");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something went wrong!!!");
		}
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee){
		
		logger.info("update employee controller started ------ ");
		
		Employee response = empService.updateEmployee(id, employee);
		
		if(response != null) {
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}

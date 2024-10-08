package me.crud_backend.controller;

import java.util.List;

import me.crud_backend.dto.EmployeeDetailsDTO;
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
@RequestMapping("/api/emp")
@CrossOrigin("http://localhost:3000")
@SuppressWarnings("unused")
public class CrudController {
	
	@Autowired
	EmployeeService empService;
	
	// Create logger instance
    private static final Logger logger = LoggerFactory.getLogger(CrudController.class);
	
	@PostMapping("/add")
	public ResponseEntity<String> addEmployee(@RequestBody EmployeeDetailsDTO request) {
		
		logger.info("add employee controller started ------- ");
		System.out.println(request);

		boolean flg = empService.addEmployee(request);

		if(flg){
			logger.info("Record saved successfully!!!");

			return ResponseEntity.status(HttpStatus.CREATED).body("Record Saved Successfully");
		} else{
			logger.error("Something went wrong");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
		}
	}

	@GetMapping("/employees")
	public ResponseEntity<List<EmployeeDetailsDTO>> getEmployeeList(){
		
		logger.info("employees controller started ----");
		
		List<EmployeeDetailsDTO> response = empService.getAllEmployees();

		if(response != null){
			logger.info("Records fetched successfully");
			return ResponseEntity.ok(response);
		} else {
			logger.error("Empty records");
			return ResponseEntity.notFound().build();
		}


	}

	@GetMapping("/get/{id}")
	public ResponseEntity<EmployeeDetailsDTO> getEmployee(@PathVariable Long id){

		logger.info("get Employee by Id controller started ---- ");

		EmployeeDetailsDTO response = empService.getEmployeeWithDepartment(id);

		if(response != null){
			logger.info("Record fetched successfully ::: {}", response);

			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@GetMapping("/getByEmpId/{empId}")
	public ResponseEntity<EmployeeDetailsDTO> getEmployeeByEmpId(@PathVariable String empId){
		
		logger.info("getByEmpId controller started ---------- ");
		
		EmployeeDetailsDTO response = empService.getEmployeeByEmpId(empId);
		
		System.out.println("Employee details :::: " + response);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable Long id){
		logger.info("delete employee controller started -------- ");
		
		if(empService.deleteEmployee(id)) {
			logger.info("Record deleted successfully!!!");
			return ResponseEntity.status(HttpStatus.OK).body("Employee Id : " + id +" deleted Successfully!!!");
		} else {
			logger.error("Something went wrong!!!");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something went wrong!!!");
		}
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDetailsDTO employee){
		
		logger.info("update employee controller started ------   ");
		
		boolean response = empService.updateEmployee(id, employee);
		
		if(response) {
			return ResponseEntity.status(HttpStatus.OK).body("Employee id: " + id + " updated successfully");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Record not found");
		}
	}

	@GetMapping("/test")
	public String test(){
		return "This is Crud project";
	}
}

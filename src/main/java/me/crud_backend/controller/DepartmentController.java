package me.crud_backend.controller;

import me.crud_backend.pojo.Department;
import me.crud_backend.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/department")
@CrossOrigin("http://localhost:3000")
@SuppressWarnings("unused")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    private static final Logger logger = LoggerFactory.getLogger(Department.class);

    @PostMapping("add")
    public ResponseEntity<String> addDepartment(@RequestBody Department request) {
        try{
            logger.info("Add Department controller started ----");

            boolean flg = departmentService.addDepartment(request);

            if(flg){
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("Record Saved Successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

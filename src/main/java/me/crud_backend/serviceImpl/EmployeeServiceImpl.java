package me.crud_backend.serviceImpl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import me.crud_backend.dto.EmployeeDetailsDTO;
import me.crud_backend.pojo.Department;
import me.crud_backend.pojo.Employee;
import me.crud_backend.repository.DepartmentRepository;
import me.crud_backend.repository.EmployeeRepository;
import me.crud_backend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository empRepository;

    @Autowired
    DepartmentRepository deptRepository;

    @Override
    public boolean addEmployee(EmployeeDetailsDTO employee) {

        try {
            Optional<Department> tempDepartment = deptRepository.findByDepartmentName(employee.getDepartmentName());

            if (tempDepartment.isPresent()) {

                Department existingDepartment = tempDepartment.get();

                Employee emp = new Employee();
                emp.setEmpId(employee.getEmpId());
                emp.setName(employee.getEmpName());
                emp.setPhone(employee.getEmpPhone());
                emp.setEmail(employee.getEmpEmail());

                emp.setDepartment(existingDepartment);

                empRepository.save(emp);

                return true;
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public Employee getEmployee(Long id) {
        return empRepository.findById(id).orElse(null);
    }

    @Override
    public EmployeeDetailsDTO getEmployeeWithDepartment(Long id) {
        try {
            EmployeeDetailsDTO response = empRepository.findEmployeeWithDepartment(id);
            System.out.println(response.getDepartmentName());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<EmployeeDetailsDTO> getAllEmployees() {
        try {
            return empRepository.findAllEmployeeDetails();
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return null;
    }

    @Override
    public Employee getEmployeeByEmpId(String empId) {
        return empRepository.findByEmpId(empId).orElse(null);
    }


    @Transactional
    @Override
    public boolean deleteEmployee(Long id) {

        try {
            if (empRepository.existsById(id)) {
                empRepository.deleteEmployeeById(id);
                System.out.println("Employee Id : " + id + "deleted successfully.");
                return true;
            } else {
                System.out.println("Employee Id : " +id + "is not able to delete.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Employee Id : " +id + "is not able to delete.");
            e.fillInStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateEmployee(Long id, EmployeeDetailsDTO request) {
        try {
            Employee existingEmployee = empRepository.findById(id).orElse(null);

            if (existingEmployee != null) {
                System.out.println("record is present-----");

                Optional<Department> tempDepartment = deptRepository.findByDepartmentName(request.getDepartmentName());

                if (tempDepartment.isPresent()) {
                    Department department = tempDepartment.get();

                    existingEmployee.setEmpId(request.getEmpId());
                    existingEmployee.setName(request.getEmpName());
                    existingEmployee.setEmail(request.getEmpEmail());
                    existingEmployee.setPhone(request.getEmpPhone());

                    existingEmployee.setDepartment(department);

                    empRepository.save(existingEmployee);
                }
            }
            return true;
        } catch (Exception e) {
            e.fillInStackTrace();
            return false;
        }
    }
}

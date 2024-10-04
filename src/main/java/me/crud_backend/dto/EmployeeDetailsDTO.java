package me.crud_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDetailsDTO {

    private String empId;
    private String empName;
    private String empEmail;
    private long empPhone;

    private String departmentName;
}

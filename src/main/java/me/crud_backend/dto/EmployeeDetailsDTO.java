package me.crud_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDetailsDTO {

    private Long id;
    private String empid;
    private String name;
    private String email;
    private long phone;

    private String department;

    public EmployeeDetailsDTO(String empid, String name, String email, long phone, String department) {
        this.empid = empid;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.department = department;
    }
}

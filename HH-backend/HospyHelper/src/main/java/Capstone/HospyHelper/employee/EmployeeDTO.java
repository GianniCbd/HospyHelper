package Capstone.HospyHelper.employee;

import Capstone.HospyHelper.Enums.RoleEmployee;

public record EmployeeDTO(
        String name,
        String surname,
        int age,
        String email,
        double salary,
        RoleEmployee roleEmployee
) {
}

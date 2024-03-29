package Capstone.HospyHelper.employee;

import Capstone.HospyHelper.Enums.RoleEmployee;
import Capstone.HospyHelper.accommodation.Accommodation;

public record EmployeeDTO(
        String name,
        String surname,
        int age,
        String email,
        double salary,
        RoleEmployee roleEmployee,
        Accommodation accommodation
) {
}

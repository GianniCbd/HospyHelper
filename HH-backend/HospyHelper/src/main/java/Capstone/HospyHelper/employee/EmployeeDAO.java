package Capstone.HospyHelper.employee;

import Capstone.HospyHelper.Enums.RoleEmployee;
import Capstone.HospyHelper.accommodation.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface EmployeeDAO extends JpaRepository<Employee,Long> {

    List<Employee> findByAgeGreaterThanEqual(int age);
    List<Employee> findBySalaryGreaterThan(double salary);
    List<Employee> findByRoleEmployee(RoleEmployee roleEmployee);
    List<Employee> findAllByOrderBySalaryDesc();
    List<Employee> findByNameContaining(String partialName);
    List<Employee> findBySalaryBetween(double minSalary, double maxSalary);


    Collection<? extends Employee> findByAccommodation(Accommodation accommodation);
}

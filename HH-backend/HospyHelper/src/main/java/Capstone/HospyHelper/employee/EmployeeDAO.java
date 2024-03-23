package Capstone.HospyHelper.employee;

import Capstone.HospyHelper.Enums.RoleEmployee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeDAO extends JpaRepository<Employee,Long> {

    List<Employee> findByAgeGreaterThanEqual(int age);
    List<Employee> findBySalaryGreaterThan(double salary);
    List<Employee> findByRoleEmployee(RoleEmployee roleEmployee);
    List<Employee> findAllByOrderBySalaryDesc();
    List<Employee> findByNameContaining(String partialName);
    List<Employee> findBySalaryBetween(double minSalary, double maxSalary);

    Page<Employee> findByOwnerId(UUID ownerId, Pageable pageable);


}

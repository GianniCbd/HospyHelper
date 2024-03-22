package Capstone.HospyHelper.employee;

import Capstone.HospyHelper.Enums.RoleEmployee;
import Capstone.HospyHelper.accommodation.Accommodation;
import Capstone.HospyHelper.accommodation.AccommodationDAO;
import Capstone.HospyHelper.auth.User;
import Capstone.HospyHelper.exceptions.NotFoundException;
import Capstone.HospyHelper.services.StatisticOperation;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeSRV {

    @Autowired
    EmployeeDAO employeeDAO;
    @Autowired
    StatisticOperation statisticOperation;
    @Autowired
    private AccommodationDAO accommodationDAO;

    public Page<Employee> getAll(int pageNumber, int pageSize, String orderBy) throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = null;
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            currentUser = (User) authentication.getPrincipal();
        } else {
            throw new IllegalStateException("User not authenticated");
        }
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));
        Page<Accommodation> userAccommodations = accommodationDAO.findByUser(currentUser, pageable);
        List<Employee> userEmployee = new ArrayList<>();
        for (Accommodation accommodation : userAccommodations) {
            userEmployee.addAll(employeeDAO.findByAccommodation(accommodation));
        }
        int start = (int) pageable.getOffset();
        int end = (start + pageable.getPageSize()) > userEmployee.size() ? userEmployee.size() : (start + pageable.getPageSize());
        Page<Employee> userEmployeePage = new PageImpl<>(userEmployee.subList(start, end), pageable, userEmployee.size());
        return userEmployeePage;
    }

    public Employee saveEmployee(EmployeeDTO employeeDTO, Long accommodation_id) {
        Accommodation accommodation = accommodationDAO.findById(accommodation_id)
                .orElseThrow(() -> new EntityNotFoundException("Accommodation with id " + accommodation_id + " not found"));

        Employee employee = new Employee(
                employeeDTO.name(),
                employeeDTO.surname(),
                employeeDTO.age(),
                employeeDTO.email(),
                employeeDTO.salary(),
                employeeDTO.roleEmployee(),
                accommodation
        );
        return employeeDAO.save(employee);
    }

    public Employee getEmployeeById(Long id) {
        return employeeDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }
    public Employee updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee existingEmployee = employeeDAO.findById(id).orElseThrow(() -> new NotFoundException("Room not found with ID: " + id));
        existingEmployee.setName(employeeDTO.name());
        existingEmployee.setSurname(employeeDTO.surname());
        existingEmployee.setAge(employeeDTO.age());
        existingEmployee.setEmail(employeeDTO.email());
        existingEmployee.setSalary(employeeDTO.salary());
        existingEmployee.setRoleEmployee(employeeDTO.roleEmployee());
        return employeeDAO.save(existingEmployee);
    }
    public void deleteEmployee(Long id) {
        Employee employee = this.getEmployeeById(id);
        employeeDAO.delete(employee);
    }
    //**************************************************************************************************


    public List<EmployeeDTO> findEmployeeDTOsByAgeGreaterThanEqual(int age) {
        List<Employee> employees = employeeDAO.findByAgeGreaterThanEqual(age);
        return mapEmployeesToDTOs(employees);
    }

    public List<EmployeeDTO> findEmployeeDTOsBySalaryGreaterThan(double salary) {
        List<Employee> employees = employeeDAO.findBySalaryGreaterThan(salary);
        return mapEmployeesToDTOs(employees);
    }

    public List<EmployeeDTO> findEmployeeDTOsByRoleEmployee(RoleEmployee roleEmployee) {
        List<Employee> employees = employeeDAO.findByRoleEmployee(roleEmployee);
        return mapEmployeesToDTOs(employees);
    }


    public List<EmployeeDTO> findAllEmployeeDTOsOrderBySalaryDesc() {
        List<Employee> employees = employeeDAO.findAllByOrderBySalaryDesc();
        return mapEmployeesToDTOs(employees);
    }

    public List<EmployeeDTO> findEmployeeDTOsByNameContaining(String partialName) {
        List<Employee> employees = employeeDAO.findByNameContaining(partialName);
        return mapEmployeesToDTOs(employees);
    }

    public List<EmployeeDTO> findEmployeeDTOsBySalaryBetween(double minSalary, double maxSalary) {
        List<Employee> employees = employeeDAO.findBySalaryBetween(minSalary, maxSalary);
        return mapEmployeesToDTOs(employees);
    }

    private List<EmployeeDTO> mapEmployeesToDTOs(List<Employee> employees) {
        return employees.stream()
                .map(employee -> new EmployeeDTO(
                        employee.getName(),
                        employee.getSurname(),
                        employee.getAge(),
                        employee.getEmail(),
                        employee.getSalary(),
                        employee.getRoleEmployee()
                ))
                .collect(Collectors.toList());
    }


}

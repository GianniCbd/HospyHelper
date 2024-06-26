package Capstone.HospyHelper.employee;

import Capstone.HospyHelper.Enums.RoleEmployee;
import Capstone.HospyHelper.auth.User;
import Capstone.HospyHelper.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeCTRL {
    @Autowired
    EmployeeSRV employeeSRV;

    @GetMapping
    public ResponseEntity<Page<Employee>> getUserRooms(@AuthenticationPrincipal User currentAuthenticatedUser,
                                                   @RequestParam(defaultValue = "0") int pageNumber,
                                                   @RequestParam(defaultValue = "4") int pageSize
    ) {
        Page<Employee> userEmployeePage = employeeSRV.getAll(currentAuthenticatedUser.getId(), pageNumber, pageSize);
        System.out.println(userEmployeePage);
        return new ResponseEntity<>(userEmployeePage, HttpStatus.OK);
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Employee> saveEmployee(@AuthenticationPrincipal User currentAuthenticatedUser,@RequestBody @Validated EmployeeDTO employeeDTO, BindingResult validation) throws IOException {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        Employee savedEmployee = employeeSRV.saveEmployee(employeeDTO,currentAuthenticatedUser);
        return ResponseEntity.ok(savedEmployee);
    }

    @GetMapping("/{id}")
    public Employee findById(@PathVariable Long id) {
        return this.employeeSRV.getEmployeeById(id);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
        return employeeSRV.updateEmployee(id,employeeDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeSRV.deleteEmployee(id);
    }

    //***********************************************************************************

    @GetMapping("/age-greater-than")
    public List<EmployeeDTO> findEmployeeDTOsByAgeGreaterThanEqual(@RequestParam("age") int age) {
        return employeeSRV.findEmployeeDTOsByAgeGreaterThanEqual(age);
    }

    @GetMapping("/salary-greater-than")
    public List<EmployeeDTO> findEmployeeDTOsBySalaryGreaterThan(@RequestParam("salary") double salary) {
        return employeeSRV.findEmployeeDTOsBySalaryGreaterThan(salary);
    }

    @GetMapping("/role")
    public List<EmployeeDTO> findEmployeeDTOsByRoleEmployee(@RequestParam("role") RoleEmployee roleEmployee) {
        return employeeSRV.findEmployeeDTOsByRoleEmployee(roleEmployee);
    }


    @GetMapping("/all-sorted-by-salary-desc")
    public List<EmployeeDTO> findAllEmployeeDTOsOrderBySalaryDesc() {
        return employeeSRV.findAllEmployeeDTOsOrderBySalaryDesc();
    }

    @GetMapping("/name-containing")
    public List<EmployeeDTO> findEmployeeDTOsByNameContaining(@RequestParam("partialName") String partialName) {
        return employeeSRV.findEmployeeDTOsByNameContaining(partialName);
    }

    @GetMapping("/salary-between")
    public List<EmployeeDTO> findEmployeeDTOsBySalaryBetween(@RequestParam("minSalary") double minSalary,
                                                             @RequestParam("maxSalary") double maxSalary) {
        return employeeSRV.findEmployeeDTOsBySalaryBetween(minSalary, maxSalary);
    }

    @GetMapping("/{id}/salary")
    public double calculateEmployeeSalary(@PathVariable Long id) {
        Employee employee = employeeSRV.getEmployeeById(id);
        if (employee == null) {
            return 0.0;
        }
        return employee.getSalary();
    }
}

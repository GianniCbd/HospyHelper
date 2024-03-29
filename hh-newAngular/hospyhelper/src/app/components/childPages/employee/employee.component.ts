import { Component, OnInit } from '@angular/core';
import { Accommodation } from 'src/app/models/accommodation';
import { Employee, RoleEmployee } from 'src/app/models/employee';
import { Page } from 'src/app/models/page';
import { AccommodationService } from 'src/app/services/accommodation.service';
import { EmployeeService } from 'src/app/services/employee.service';

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrls: ['./employee.component.scss'],
})
export class EmployeeComponent implements OnInit {
  employee: Employee[] = [];
  showCard: boolean = false;
  newEmployee: any = {};
  accommodation: Accommodation[] = [];
  accommodationId!: number;
  editingEmployee: any = null;
  totalSalary: number = 0;
  showTotalSalary: boolean = false;

  selectedEmployee: any;
  showCard2: boolean = false;

  page!: Page<Employee>;
  currentPage: number = 0;
  totalPages!: number;
  roles = Object.values(RoleEmployee);

  constructor(
    private employeeSrv: EmployeeService,
    private accommodationService: AccommodationService
  ) {}

  ngOnInit(): void {
    this.fetchAccommodation();
    this.fetchEmployee();
  }

  openEmployeeCard(employee: any) {
    this.showCard2 = true;
    this.selectedEmployee = employee;
  }

  fetchAccommodation() {
    this.accommodationService.getAccommodation().subscribe(
      (data: any) => {
        this.accommodation = data;
      },
      (error) => {
        console.error('Error retrieving rooms', error);
      }
    );
  }

  calculateTotalSalary() {
    this.totalSalary = 0;
    this.employee.forEach((employee) => {
      this.employeeSrv.getEmployeeSalary(employee.id).subscribe(
        (salary: number) => {
          this.totalSalary += salary;
        },
        (error) => {
          console.error('Error loading salary for employee: ', error);
        }
      );
    });
  }

  toggleTotalSalaryVisibility() {
    this.showTotalSalary = !this.showTotalSalary;
    if (this.showTotalSalary) {
      this.calculateTotalSalary();
    }
  }

  fetchEmployee(page: number = 0, size: number = 20) {
    this.employeeSrv.getEmployee(page, size).subscribe(
      (data: Page<Employee>) => {
        this.employee = data.content;
        console.log(data.content);
        this.page = data;
        this.currentPage = data.number;
        this.totalPages = data.totalPages;
      },
      (error) => {
        console.error('Errore durante il recupero dei dipendenti:', error);
      }
    );
  }

  nextPage() {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.fetchEmployee(this.currentPage, this.page.size);
    }
  }

  prevPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.fetchEmployee(this.currentPage, this.page.size);
    }
  }

  selectAccommodation(accommodation: Accommodation): void {
    this.accommodationId = accommodation.id;
  }

  addNewEmployee() {
    this.employeeSrv.saveEmployee(this.newEmployee).subscribe(
      (response) => {
        this.employee.push(response);
        this.newEmployee = {};
        this.showCard = false;
      },
      (error) => {
        console.error("Errore durante l'aggiunta del dipendente:", error);
      }
    );
  }

  toggleCardVisibility() {
    this.showCard = !this.showCard;
  }
  close() {
    this.showCard = false;
  }

  toggleDetails(employee: Employee) {
    employee.showDetails = !employee.showDetails;
  }

  editEmp(employee: any) {
    console.log('Employee object:', employee);
    const selectedEmployeeId = employee.id;
    console.log('Selected employee ID:', selectedEmployeeId);

    this.editingEmployee = { ...employee, id: selectedEmployeeId };
  }
  saveEditedEmployee() {
    this.employeeSrv
      .updateEmployee(this.editingEmployee.id, this.editingEmployee)
      .subscribe(
        (updatedEmployee) => {
          console.log('Stanza aggiornata con successo:', updatedEmployee);
          this.cancelEdit();
          this.fetchEmployee();
        },
        (error) => {
          console.error("Errore durante l'aggiornamento della stanza:", error);
        }
      );
  }

  cancelEdit() {
    this.editingEmployee = null;
  }

  deleteEmployee(employeeToDelete: Employee) {
    if (employeeToDelete && employeeToDelete.id) {
      const selectedEmployeeId = employeeToDelete.id;
      this.employeeSrv.deleteEmployee(selectedEmployeeId).subscribe(() => {
        const index = this.employee.findIndex(
          (b: Employee) => b.id === selectedEmployeeId
        );
        if (index !== -1) {
          this.employee.splice(index, 1);
        }
      });
    } else {
      console.error(
        "Errore: l'oggetto employeeToDelete o la sua proprietà 'id' sono undefined."
      );
    }
  }
}

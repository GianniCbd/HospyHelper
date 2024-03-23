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

  page!: Page<Employee>;
  currentPage: number = 0;
  totalPages!: number;

  roles = Object.values(RoleEmployee);

  constructor(
    private employeeSrv: EmployeeService,
    private accommodationService: AccommodationService
  ) {}

  ngOnInit(): void {
    this.fetchEmployee();
    this.fetchAccommodation();
  }
  fetchAccommodation() {
    this.accommodationService.getAccommodation().subscribe(
      (data: Accommodation[]) => {
        this.accommodation = data;
      },
      (error) => {
        console.error('Error retrieving rooms', error);
      }
    );
  }
  calculateTotalSalary() {
    this.totalSalary = this.employee.reduce(
      (total, emp) => total + emp.salary,
      0
    );
  }
  toggleTotalSalaryVisibility() {
    this.showTotalSalary = !this.showTotalSalary;
    if (this.showTotalSalary) {
      this.calculateTotalSalary();
    }
  }

  fetchEmployee(page: number = 0, size: number = 2) {
    this.employeeSrv.getEmployee(page, size).subscribe(
      (data: Page<Employee>) => {
        this.page = data;
        this.employee = data.content;
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
    console.log('accommodationId:', this.accommodationId);
    this.employeeSrv
      .saveEmployee(this.newEmployee, this.accommodationId)
      .subscribe(
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

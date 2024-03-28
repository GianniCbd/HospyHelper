import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Employee, RoleEmployee } from '../models/employee';
import { Page } from '../models/page';

@Injectable({
  providedIn: 'root',
})
export class EmployeeService {
  apiUrl: string = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getEmployee(
    page: number = 0,
    size: number = 2,
    orderBy: string = 'name'
  ): Observable<Page<Employee>> {
    const params = new HttpParams()
      .set('pageNumber', page.toString())
      .set('pageSize', size.toString())
      .set('orderBy', orderBy);
    return this.http.get<Page<Employee>>(`${this.apiUrl}/employee`, { params });
  }

  saveEmployee(employee: Employee): Observable<Employee> {
    const url = `${this.apiUrl}/employee/save`;
    return this.http.post<Employee>(url, employee);
  }

  updateEmployee(id: number, employee: any): Observable<Employee> {
    return this.http.put<Employee>(`${this.apiUrl}/employee/${id}`, employee);
  }
  deleteEmployee(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/employee/${id}`);
  }

  getEmployeeSalary(id: number): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/employee/${id}/salary`);
  }
}

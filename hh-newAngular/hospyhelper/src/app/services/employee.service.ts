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
  currentPage: number = 1;

  constructor(private http: HttpClient) {}

  getEmployee(page: number = 1): Observable<Employee[]> {
    const params = new HttpParams().set('page', page.toString());
    this.currentPage = page;
    return this.http
      .get<Page<Employee>>(`${this.apiUrl}/employee`, { params })
      .pipe(map((list) => list.content));
  }

  saveEmployee(
    employee: Employee,
    accommodationId: number
  ): Observable<Employee> {
    const url = `${this.apiUrl}/employee/save/${accommodationId}`;
    return this.http.post<Employee>(url, employee);
  }

  updateEmployee(id: number, employee: any): Observable<Employee> {
    return this.http.put<Employee>(`${this.apiUrl}/employee/${id}`, employee);
  }
  deleteEmployee(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}

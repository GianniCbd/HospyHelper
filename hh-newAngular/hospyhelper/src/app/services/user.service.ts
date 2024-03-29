import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../models/user';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  apiUrl: string = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getCurrentUser(): Observable<User> {
    const url = `${this.apiUrl}/users/me`;
    return this.http.get<User>(url);
  }

  updateCurrentUser(id: string, userDTO: Partial<User>): Observable<User> {
    const url = `${this.apiUrl}/users/me/${id}`;
    return this.http.put<User>(url, userDTO);
  }

  findById(id: string): Observable<User> {
    const url = `${this.apiUrl}/users/${id}`;
    return this.http.get<User>(url);
  }
}

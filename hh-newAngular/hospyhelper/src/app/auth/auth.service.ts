import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { JwtHelperService } from '@auth0/angular-jwt';
import { UserDto } from './user-dto';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { LoginData } from './login-data';
import { LoginRegisterDto } from './login-register-dto';
import { catchError } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  jwtHelper = new JwtHelperService();
  private authSubj = new BehaviorSubject<null | UserDto>(null);
  accessToken!: UserDto;
  user$ = this.authSubj.asObservable();
  apiUrl: string = environment.apiUrl;

  constructor(private http: HttpClient, private router: Router) {}

  register(data: UserDto) {
    return this.http.post(`${this.apiUrl}/auth/register`, data).pipe(
      tap(() => {
        this.router.navigate(['/login']);
      }),
      catchError((error) => {
        console.error('Registration error:', error);
        throw error;
      })
    );
  }

  login(data: LoginData): Observable<UserDto> {
    return this.http.post<UserDto>(`${this.apiUrl}/auth/login`, data).pipe(
      tap((dataLogin) => {
        this.authSubj.next(dataLogin);
        this.accessToken = dataLogin;
        localStorage.setItem('user', JSON.stringify(dataLogin));
        console.log('Login effettuato');
        this.router.navigate(['/home']);
      })
    );
  }

  restore() {
    const user = localStorage.getItem('user');
    if (!user) {
      return;
    }
    const userData: UserDto = JSON.parse(user);
    if (this.jwtHelper.isTokenExpired(userData.accessToken)) {
      return;
    }
    this.authSubj.next(userData);
  }

  logout() {
    this.authSubj.next(null);
    localStorage.removeItem('userToken');
    this.router.navigate(['']);
  }
}

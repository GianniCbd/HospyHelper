import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap, throwError } from 'rxjs';
import { JwtHelperService } from '@auth0/angular-jwt';
import { UserDto } from './user-dto';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { LoginData } from './login-data';
import { catchError } from 'rxjs';
import { LoginRegisterDto } from './login-register-dto';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  jwtHelper = new JwtHelperService();
  apiURL = environment.apiUrl;
  private authSubj = new BehaviorSubject<null | UserDto>(null);
  user$ = this.authSubj.asObservable();
  utente!: UserDto;

  constructor(private http: HttpClient, private router: Router) {}

  login(data: { email: string; password: string }) {
    return this.http.post<UserDto>(`${this.apiURL}/auth/login`, data).pipe(
      tap((loggato) => {
        this.authSubj.next(loggato);
        this.utente = loggato;
        localStorage.setItem('user', JSON.stringify(loggato));
        this.router.navigate(['/home']);
      }),
      catchError(this.errors)
    );
  }

  restore() {
    const user = localStorage.getItem('user');
    if (!user) {
      return;
    }
    const userData: UserDto = JSON.parse(user);
    if (this.jwtHelper.isTokenExpired(userData.accessToken)) {
      this.router.navigate(['/login']);
      return;
    }
    this.authSubj.next(userData);
  }

  register(data: {
    name: string;
    surname: string;
    username: string;
    email: string;
    password: string;
  }) {
    return this.http.post(`${this.apiURL}/auth/register`, data).pipe(
      tap(() => {
        this.router.navigate(['/login']), catchError(this.errors);
      })
    );
  }

  getAllUsers() {
    return this.http.get<UserDto[]>(`${this.apiURL}/users`);
  }

  logout() {
    this.authSubj.next(null);
    localStorage.removeItem('user');
    this.router.navigate(['/']);
  }

  private errors(err: any) {
    console.log(err);
    switch (err.error) {
      case 'Email already exists':
        return throwError('E-mail already registered');
        break;

      case 'Email format is invalid':
        return throwError('E-mail format not valid');
        break;

      case 'Cannot find user':
        return throwError('User does not exist');
        break;

      default:
        return throwError('Error in call');
        break;
    }
  }
}

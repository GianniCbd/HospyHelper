import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Post } from '../models/post';
import { environment } from 'src/environments/environment';
import { LoginRegisterDto } from '../auth/login-register-dto';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root',
})
export class PostService {
  apiUrl: string = environment.apiUrl;

  constructor(private http: HttpClient) {}
}

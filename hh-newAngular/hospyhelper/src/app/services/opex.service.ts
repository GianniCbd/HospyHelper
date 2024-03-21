import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Opex } from '../models/opex';
import { Page } from '../models/page';

@Injectable({
  providedIn: 'root',
})
export class OpexService {
  apiUrl: string = environment.apiUrl;
  currentPage: number = 1;
  constructor(private http: HttpClient) {}

  getOpex(page: number = 1): Observable<Opex[]> {
    const params = new HttpParams().set('page', page.toString());
    this.currentPage = page;
    return this.http
      .get<Page<Opex>>(`${this.apiUrl}/operation-expenses`, { params })
      .pipe(map((list) => list.content));
  }

  saveOpex(opex: Opex, accommodationId: number): Observable<Opex> {
    const url = `${this.apiUrl}/operation-expenses/save/${accommodationId}`;
    return this.http.post<Opex>(url, opex);
  }

  updateOpex(id: number, opex: any): Observable<Opex> {
    return this.http.put<Opex>(`${this.apiUrl}/operation-expenses/${id}`, opex);
  }
  deleteOpex(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/operation-expenses/${id}`);
  }
}

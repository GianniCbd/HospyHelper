import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Accommodation } from '../models/accommodation';
import { Observable, map } from 'rxjs';
import { Page } from '../models/page';

@Injectable({
  providedIn: 'root',
})
export class AccommodationService {
  apiUrl: string = environment.apiUrl;
  currentPage: number = 0;

  constructor(private http: HttpClient) {}

  getAccommodation(page: number = 0): Observable<Accommodation[]> {
    const params = new HttpParams().set('page', page.toString());
    this.currentPage = page;
    return this.http
      .get<Page<Accommodation>>(`${this.apiUrl}/accommodation`, { params })
      .pipe(map((list) => list.content));
  }

  findById(id: number): Observable<Accommodation> {
    const url = `${this.apiUrl}/accommodation/${id}`;
    return this.http.get<Accommodation>(url);
  }

  // createAccommodation(
  //   newAccommodation: Accommodation
  // ): Observable<Accommodation> {
  //   const url = `${this.apiUrl}/accommodation/save`;
  //   return this.http.put<Accommodation>(url, newAccommodation);
  // }

  createAccommodation(
    data: Partial<Accommodation>,
    userId: string
  ): Observable<Accommodation> {
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + this.getAccessToken,
      'Content-Type': 'application/json',
    });
    return this.http.post<Accommodation>(
      `${this.apiUrl}/accommodation/save?userId=${userId}`,
      data,
      { headers }
    );
  }

  updateAccommodation(
    id: number,
    accommodation: Accommodation
  ): Observable<Accommodation> {
    const url = `${this.apiUrl}/accommodation/${id}`;
    return this.http.put<Accommodation>(url, accommodation);
  }

  private getAccessToken(): string {
    return localStorage.getItem('access_token') || '';
  }

  deleteAccommodation(id: number): Observable<void> {
    const url = `${this.apiUrl}/accommodation/${id}`;
    return this.http.delete<void>(url);
  }
}

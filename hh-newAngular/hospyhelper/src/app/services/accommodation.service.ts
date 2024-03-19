import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Accommodation } from '../models/accommodation';
import { Observable, catchError, map, switchMap, throwError } from 'rxjs';
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

  createAccommodation(
    id: number,
    newAccommodation: Accommodation
  ): Observable<Accommodation> {
    const url = `${this.apiUrl}/accommodation/save/${id}`;
    return this.http.put<Accommodation>(url, newAccommodation);
  }

  updateAccommodation(
    id: number,
    accommodation: Accommodation
  ): Observable<Accommodation> {
    const url = `${this.apiUrl}/accommodation/${id}`;
    return this.http.put<Accommodation>(url, accommodation);
  }
}

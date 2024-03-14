import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Accommodation } from '../models/accommodation';
import { Observable, catchError, switchMap, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AccommodationService {
  apiUrl: string = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getAccommodation() {
    const url = `${this.apiUrl}/accommodation/me`;
    return this.http.get<Accommodation[]>(url);
  }

  getAccommodations(userId: string): Observable<Accommodation[]> {
    const url = `${this.apiUrl}/accommodation/byUser/${userId}`;
    return this.http.get<Accommodation[]>(url);
  }

  updateAccommodation(
    id: number,
    accommodation: Accommodation
  ): Observable<Accommodation> {
    const url = `${this.apiUrl}/accommodation/${id}`;
    return this.http.put<Accommodation>(url, accommodation);
  }
}

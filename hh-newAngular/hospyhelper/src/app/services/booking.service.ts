import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Booking } from '../models/booking';
import { Page } from '../models/page';

@Injectable({
  providedIn: 'root',
})
export class BookingService {
  apiUrl: string = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getBooking(
    page: number = 0,
    pageSize: number = 3,
    orderBy: string = 'fullName'
  ): Observable<Page<Booking>> {
    const params = new HttpParams()
      .set('pageNumber', page.toString())
      .set('pageSize', pageSize.toString())
      .set('orderBy', orderBy);
    return this.http.get<Page<Booking>>(`${this.apiUrl}/booking/all`, {
      params,
    });
  }

  createBooking(newBooking: Booking) {
    const url = `${this.apiUrl}/booking/save`;
    return this.http.post<Booking>(url, newBooking);
  }

  updateBooking(id: number, booking: Booking): Observable<Booking> {
    const url = `${this.apiUrl}/booking/${id}`;
    return this.http.put<Booking>(url, booking);
  }

  deleteBooking(id: number): Observable<void> {
    const url = `${this.apiUrl}/booking/${id}`;
    return this.http.delete<void>(url);
  }

  // ********************************************
  findByEmail(email: string): Observable<Booking[]> {
    return this.http.get<Booking[]>(
      `${this.apiUrl}/booking/by-email?email=${email}`
    );
  }

  findByFullNameAndPhone(
    fullName: string,
    phone: string
  ): Observable<Booking[]> {
    return this.http.get<Booking[]>(
      `${this.apiUrl}/booking/by-fullness-and-phone?fullName=${fullName}&phone=${phone}`
    );
  }

  findByPartialName(partialName: string): Observable<Booking[]> {
    return this.http.get<Booking[]>(
      `${this.apiUrl}/booking/findByPartialName/${partialName}`
    );
  }

  findBookingsByCheckInBetween(
    startCheckIn: string,
    endCheckIn: string
  ): Observable<Booking[]> {
    return this.http.get<Booking[]>(
      `${this.apiUrl}/booking/checkIn-between?startCheckIn=${startCheckIn}&endCheckIn=${endCheckIn}`
    );
  }

  findBookingsByCheckOutBetween(
    startCheckOut: string,
    endCheckOut: string
  ): Observable<Booking[]> {
    return this.http.get<Booking[]>(
      `${this.apiUrl}/booking/checkOut-between?startCheckOut=${startCheckOut}&endCheckOut=${endCheckOut}`
    );
  }

  getTotalGuestsByDate(targetDate: string): Observable<number> {
    return this.http.get<number>(
      `${this.apiUrl}/totalGuestsByDate?targetDate=${targetDate}`
    );
  }
}

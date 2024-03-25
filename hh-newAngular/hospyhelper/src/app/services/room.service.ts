import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Room } from '../models/room';
import { Page } from '../models/page';

@Injectable({
  providedIn: 'root',
})
export class RoomService {
  apiUrl: string = environment.apiUrl;
  currentPage: number = 0;

  constructor(private http: HttpClient) {}

  getRoom(
    pageNumber: number = 0,
    pageSize: number = 4,
    orderBy: string = 'id'
  ): Observable<Page<Room>> {
    const params = new HttpParams()
      .set('pageNumber', pageNumber.toString())
      .set('pageSize', pageSize.toString())
      .set('orderBy', orderBy);
    return this.http.get<Page<Room>>(`${this.apiUrl}/room/all`, { params });
  }

  getRoomById(id: number): Observable<Room> {
    return this.http.get<Room>(`${this.apiUrl}/room/${id}`);
  }

  createRoom(newRoom: Room) {
    const url = `${this.apiUrl}/room/save`;
    return this.http.post<Room>(url, newRoom);
  }

  updateRoom(id: number, room: Room): Observable<Room> {
    const url = `${this.apiUrl}/room/${id}`;
    return this.http.put<Room>(url, room);
  }

  deleteRoom(id: number): Observable<void> {
    const url = `${this.apiUrl}/room/${id}`;
    return this.http.delete<void>(url);
  }
  // ********************************************************
  getRoomsOrderByPriceAsc(
    pageNumber: number = 0,
    pageSize: number = 4
  ): Observable<Page<Room>> {
    const params = new HttpParams()
      .set('pageNumber', pageNumber.toString())
      .set('pageSize', pageSize.toString());

    return this.http.get<Page<Room>>(`${this.apiUrl}/room/orderByPriceAsc`, {
      params,
    });
  }

  getRoomsOrderByPriceDesc(
    pageNumber: number = 0,
    pageSize: number = 4
  ): Observable<Page<Room>> {
    const params = new HttpParams()
      .set('pageNumber', pageNumber.toString())
      .set('pageSize', pageSize.toString());

    return this.http.get<Page<Room>>(`${this.apiUrl}/room/orderByPriceDesc`, {
      params,
    });
  }
  getRoomsOrderByRoomNumberAsc(
    pageNumber: number = 0,
    pageSize: number = 4
  ): Observable<Page<Room>> {
    const params = new HttpParams()
      .set('pageNumber', pageNumber.toString())
      .set('pageSize', pageSize.toString());
    return this.http.get<Page<Room>>(
      `${this.apiUrl}/room/orderByRoomNumberAsc`,
      { params }
    );
  }
  getRoomsOrderByRoomNumberDesc(
    pageNumber: number = 0,
    pageSize: number = 4
  ): Observable<Page<Room>> {
    const params = new HttpParams()
      .set('pageNumber', pageNumber.toString())
      .set('pageSize', pageSize.toString());
    return this.http.get<Page<Room>>(
      `${this.apiUrl}/room/orderByRoomNumberDesc`,
      { params }
    );
  }
}

import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Room } from '../models/room';
import { Page } from '../models/page';
import { RoomType } from '../models/room-type';

@Injectable({
  providedIn: 'root',
})
export class RoomService {
  apiUrl: string = environment.apiUrl;
  currentPage: number = 1;

  constructor(private http: HttpClient) {}

  getRoom(
    pageNumber: number = 0,
    pageSize: number = 4
  ): Observable<Page<Room>> {
    const params = new HttpParams()
      .set('pageNumber', pageNumber.toString())
      .set('pageSize', pageSize.toString());

    return this.http.get<Page<Room>>(`${this.apiUrl}/room`, { params });
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
  getRoomsOrderByPriceAsc(): Observable<Room[]> {
    return this.http.get<Room[]>(`${this.apiUrl}/room/order-by-price-asc`);
  }

  getRoomsOrderByPriceDesc(): Observable<Room[]> {
    return this.http.get<Room[]>(`${this.apiUrl}/room/order-by-price-desc`);
  }
  getRoomsOrderByRoomNumberAsc(): Observable<Room[]> {
    return this.http.get<Room[]>(
      `${this.apiUrl}/room/order-by-room-number-asc`
    );
  }
  getRoomsOrderByRoomNumberDesc(): Observable<Room[]> {
    return this.http.get<Room[]>(
      `${this.apiUrl}/room/order-by-room-number-desc`
    );
  }
}

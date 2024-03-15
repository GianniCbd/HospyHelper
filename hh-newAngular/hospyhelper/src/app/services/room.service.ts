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
  currentPage: number = 1;

  constructor(private http: HttpClient) {}

  getRoom(page: number = 1): Observable<Room[]> {
    const params = new HttpParams().set('page', page.toString());
    this.currentPage = page;
    return this.http
      .get<Page<Room>>(`${this.apiUrl}/room`, { params })
      .pipe(map((list) => list.content));
  }

  createRoom(newRoom: Room) {
    const url = `${this.apiUrl}/room/save`;
    return this.http.post<Room>(url, newRoom);
  }

  updateRoom(id: number, room: Room): Observable<Room> {
    const url = `${this.apiUrl}/room/${id}`;
    return this.http.put<Room>(url, room);
  }

  deleteRoomType(id: number): Observable<void> {
    const url = `${this.apiUrl}/room/${id}`;
    return this.http.delete<void>(url);
  }
}

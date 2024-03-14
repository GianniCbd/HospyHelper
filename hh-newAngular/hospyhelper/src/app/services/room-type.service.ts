import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { RoomType } from '../models/room-type';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class RoomTypeService {
  apiUrl: string = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getRoomType() {
    const url = `${this.apiUrl}/roomType`;
    return this.http.get<RoomType[]>(url);
  }

  getRoomTypeById(id: number): Observable<RoomType> {
    const url = `${this.apiUrl}/roomType/${id}`;
    return this.http.get<RoomType>(url);
  }

  createRoomType(newRoomType: RoomType): Observable<RoomType> {
    const url = `${this.apiUrl}/roomType`;
    return this.http.post<RoomType>(url, newRoomType);
  }

  updateRoomType(id: number, roomTypeDTO: RoomType): Observable<RoomType> {
    const url = `${this.apiUrl}/room-types/${id}`;
    return this.http.put<RoomType>(url, roomTypeDTO);
  }

  deleteRoomType(id: number): Observable<void> {
    const url = `${this.apiUrl}/room-types/${id}`;
    return this.http.delete<void>(url);
  }

  uploadAvatar(id: number, image: File): Observable<string> {
    const formData = new FormData();
    formData.append('image', image);
    const url = `${this.apiUrl}/room-types/upload/${id}`;
    return this.http.patch<string>(url, formData);
  }
}

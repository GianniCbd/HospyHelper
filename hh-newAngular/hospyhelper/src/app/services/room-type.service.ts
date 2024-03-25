import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { RoomType } from '../models/room-type';
import { Observable, map } from 'rxjs';
import { Page } from '../models/page';

@Injectable({
  providedIn: 'root',
})
export class RoomTypeService {
  apiUrl: string = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getRoomType(
    pageNumber: number = 0,
    pageSize: number = 4,
    orderBy: string = 'typeName'
  ): Observable<Page<RoomType>> {
    const params = new HttpParams()
      .set('pageNumber', pageNumber.toString())
      .set('pageSize', pageSize.toString())
      .set('orderBy', orderBy);

    return this.http.get<Page<RoomType>>(`${this.apiUrl}/roomTypes/all`, {
      params,
    });
  }

  getRoomTypeById(id: number) {
    const url = `${this.apiUrl}/roomTypes/${id}`;
    return this.http.get<RoomType>(url);
  }

  createRoomType(newRoomType: RoomType) {
    const url = `${this.apiUrl}/roomTypes`;
    return this.http.post<RoomType>(url, newRoomType);
  }

  updateRoomType(id: number, roomTypeDTO: RoomType): Observable<RoomType> {
    const url = `${this.apiUrl}/roomTypes/${id}`;
    return this.http.put<RoomType>(url, roomTypeDTO);
  }

  deleteRoomType(id: number): Observable<void> {
    const url = `${this.apiUrl}/roomTypes/${id}`;
    return this.http.delete<void>(url);
  }

  uploadAvatar(id: number, image: File): Observable<string> {
    const formData = new FormData();
    formData.append('image', image);
    const url = `${this.apiUrl}/roomTypes/upload/${id}`;
    return this.http.patch(url, formData, { responseType: 'text' });
  }
}

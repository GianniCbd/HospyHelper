import { Component, OnInit } from '@angular/core';
import { RoomType } from 'src/app/models/room-type';
import { RoomTypeService } from 'src/app/services/room-type.service';

@Component({
  selector: 'app-room-type',
  templateUrl: './room-type.component.html',
  styleUrls: ['./room-type.component.scss'],
})
export class RoomTypeComponent implements OnInit {
  roomTypes: RoomType[] = [];

  showEditFields: boolean = false;
  editedRoomType: any = {
    typeName: '',
    description: '',
    image: '',
  };

  constructor(private roomTypeService: RoomTypeService) {}

  ngOnInit(): void {
    this.fetchRoomTypes();
  }

  fetchRoomTypes() {
    this.roomTypeService.getRoomType().subscribe(
      (data) => {
        this.roomTypes = data;
        console.log(this.roomTypes);
      },
      (error) => {
        console.error('Errore durante il recupero dei tipi di stanza:', error);
      }
    );
  }

  toggleEditFields() {
    this.showEditFields = !this.showEditFields;
  }
}

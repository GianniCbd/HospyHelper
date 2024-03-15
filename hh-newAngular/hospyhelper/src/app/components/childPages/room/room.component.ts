import { Component, OnInit } from '@angular/core';
import { Room } from 'src/app/models/room';
import { RoomService } from 'src/app/services/room.service';

@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrls: ['./room.component.scss'],
})
export class RoomComponent implements OnInit {
  room: Room[] = [];
  editingRoom: any = null;
  newRoom: any = {};

  constructor(private roomSrv: RoomService) {}

  ngOnInit(): void {
    this.fetchRoomTypes();
  }

  fetchRoomTypes() {
    this.roomSrv.getRoom().subscribe(
      (data) => {
        this.room = data;
        console.log(this.room);
      },
      (error) => {
        console.error('Errore durante il recupero di stanze', error);
      }
    );
  }

  editRoom(room: any) {
    const selectedRoomId = room.id;
    console.log('qua', selectedRoomId);
    this.editingRoom = { ...room, id: selectedRoomId };
  }
  saveEditedRoom() {
    this.roomSrv.updateRoom(this.editingRoom.id, this.editingRoom).subscribe(
      (updatedRoomType) => {
        console.log('Stanza aggiornata con successo:', updatedRoomType);
        this.cancelEdit();
      },
      (error) => {
        console.error("Errore durante l'aggiornamento della stanza:", error);
      }
    );
  }
  cancelEdit() {
    this.editingRoom = null;
  }
}

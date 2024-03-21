import { Component, OnInit } from '@angular/core';
import { Room } from 'src/app/models/room';
import { RoomType } from 'src/app/models/room-type';
import { RoomTypeService } from 'src/app/services/room-type.service';
import { RoomService } from 'src/app/services/room.service';

@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrls: ['./room.component.scss'],
})
export class RoomComponent implements OnInit {
  room: Room[] = [];
  roomTypes: RoomType[] = [];
  editingRoom: any = null;
  newRoom: any = {};
  showCard: boolean = false;
  sortedRooms!: Room[];
  selectedOrder: string = 'ricerca';

  constructor(
    private roomSrv: RoomService,
    private roomTypeService: RoomTypeService
  ) {}

  ngOnInit(): void {
    this.fetchRoom();
    this.fetchRoomTypes();
  }

  fetchRoomTypes() {
    this.roomTypeService.getRoomType().subscribe(
      (data) => {
        this.roomTypes = data;
      },
      (error) => {
        console.error('Errore durante il recupero dei tipi di stanza:', error);
      }
    );
  }
  fetchRoom(): void {
    if (this.selectedOrder === 'ricerca') {
      this.roomSrv.getRoom().subscribe((data) => {
        this.room = data;
        console.log('Rooms fetched (no order):', this.room);
      });
    } else if (this.selectedOrder === 'asc') {
      this.roomSrv.getRoomsOrderByPriceAsc().subscribe((data) => {
        this.room = data;
        console.log('Rooms fetched (asc):', this.room);
      });
    } else if (this.selectedOrder === 'desc') {
      this.roomSrv.getRoomsOrderByPriceDesc().subscribe((data) => {
        this.room = data;
        console.log('Rooms fetched (desc):', this.room);
      });
    } else if (this.selectedOrder === 'ascRoomNumber') {
      this.roomSrv.getRoomsOrderByRoomNumberAsc().subscribe((data) => {
        this.room = data;
        console.log('Rooms fetched (asc room number):', this.room);
      });
    } else if (this.selectedOrder === 'descRoomNumber') {
      this.roomSrv.getRoomsOrderByRoomNumberDesc().subscribe((data) => {
        this.room = data;
        console.log('Rooms fetched (desc room number):', this.room);
      });
    }
  }

  changeOrder(order: string): void {
    this.selectedOrder = order;
    this.fetchRoom();
  }

  editRoom(room: any) {
    const selectedRoomId = room.id;

    this.editingRoom = { ...room, id: selectedRoomId };
  }
  saveEditedRoom() {
    this.roomSrv.updateRoom(this.editingRoom.id, this.editingRoom).subscribe(
      (updatedRoomType) => {
        console.log('Stanza aggiornata con successo:', updatedRoomType);
        this.cancelEdit();
        this.fetchRoomTypes();
      },
      (error) => {
        console.error("Errore durante l'aggiornamento della stanza:", error);
      }
    );
  }

  cancelEdit() {
    this.editingRoom = null;
  }

  addNewRoom() {
    this.roomSrv.createRoom(this.newRoom).subscribe(
      (response) => {
        this.room.push(response);
        this.newRoom = {};
        this.showCard = false;
      },
      (error) => {
        console.error("Errore durante l'aggiunta della nuova stanza:", error);
      }
    );
  }
  toggleCardVisibility() {
    this.showCard = !this.showCard;
  }
  close() {
    this.showCard = false;
  }

  deleteRooms(rooms: Room[]) {
    rooms.forEach((room) => {
      this.deleteRoom(room);
    });
  }

  deleteRoom(room: Room) {
    const selectedRoomId = room.id;
    this.roomSrv.deleteRoom(selectedRoomId).subscribe(
      () => {
        const index = this.room.findIndex((r: Room) => r.id === selectedRoomId);
        if (index !== -1) {
          this.room.splice(index, 1);
        }
      },
      (error) => {
        if (error.status === 409) {
          alert(
            'Errore: La stanza è collegata a una prenotazione e non può essere eliminata.'
          );
        } else {
          console.error(
            "Errore durante l'eliminazione del tipo di stanza:",
            error
          );
        }
      }
    );
  }
}

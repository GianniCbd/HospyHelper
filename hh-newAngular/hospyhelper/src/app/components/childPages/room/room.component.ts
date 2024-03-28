import { Component, OnInit } from '@angular/core';
import { Page } from 'src/app/models/page';
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

  page!: Page<Room>;
  currentPage: number = 0;
  totalPages!: number;

  constructor(
    private roomSrv: RoomService,
    private roomTypeService: RoomTypeService
  ) {}

  ngOnInit(): void {
    this.fetchRoomTypes();
    this.fetchRoom();
  }

  fetchRoomTypes(page: number = 0, size: number = 20) {
    this.roomTypeService.getRoomType(page, size).subscribe(
      (data: any) => {
        this.roomTypes = data.content;
        this.page = data;
        this.currentPage = data.number;
        this.totalPages = data.totalPages;
      },
      (error) => {
        console.error('Errore durante il recupero dei tipi di stanza:', error);
      }
    );
  }

  fetchRoom(page: number = 0, size: number = 4) {
    if (this.selectedOrder === 'ricerca') {
      this.roomSrv.getRoom(page, size).subscribe((data: Page<Room>) => {
        this.room = data.content;
        this.page = data;
        this.currentPage = data.number;
        this.totalPages = data.totalPages;
      });
    } else if (this.selectedOrder === 'asc') {
      this.roomSrv
        .getRoomsOrderByPriceAsc(page, size)
        .subscribe((data: Page<Room>) => {
          this.room = data.content;
          this.page = data;
          this.currentPage = data.pageNumber;
          this.totalPages = data.totalPages;
        });
    } else if (this.selectedOrder === 'desc') {
      this.roomSrv
        .getRoomsOrderByPriceDesc(page, size)
        .subscribe((data: Page<Room>) => {
          this.room = data.content;
          this.page = data;
          this.currentPage = data.pageNumber;
          this.totalPages = data.totalPages;
        });
    } else if (this.selectedOrder === 'ascRoomNumber') {
      this.roomSrv
        .getRoomsOrderByRoomNumberAsc(page, size)
        .subscribe((data: Page<Room>) => {
          this.room = data.content;
          this.page = data;
          this.currentPage = data.pageNumber;
          this.totalPages = data.totalPages;
        });
    } else if (this.selectedOrder === 'descRoomNumber') {
      this.roomSrv
        .getRoomsOrderByRoomNumberDesc(page, size)
        .subscribe((data: Page<Room>) => {
          this.room = data.content;
          this.page = data;
          this.currentPage = data.pageNumber;
          this.totalPages = data.totalPages;
        });
    }
  }
  nextPage() {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.fetchRoom(this.currentPage);
    }
  }
  prevPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.fetchRoom(this.currentPage);
    }
  }

  changeOrder(order: string): void {
    this.selectedOrder = order;
    this.fetchRoom();
  }

  editRoom(room: any) {
    console.log(room);
    const selectedRoomId = room.id;
    console.log(selectedRoomId);
    this.editingRoom = { ...room, id: selectedRoomId };
  }

  saveEditedRoom() {
    this.roomSrv.updateRoom(this.editingRoom.id, this.editingRoom).subscribe(
      (updatedRoomType) => {
        this.cancelEdit();
        this.fetchRoom();
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

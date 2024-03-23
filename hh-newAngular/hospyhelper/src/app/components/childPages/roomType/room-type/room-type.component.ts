import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Observable, map } from 'rxjs';
import { AuthService } from 'src/app/auth/auth.service';
import { Page } from 'src/app/models/page';

import { RoomType } from 'src/app/models/room-type';
import { RoomTypeService } from 'src/app/services/room-type.service';

@Component({
  selector: 'app-room-type',
  templateUrl: './room-type.component.html',
  styleUrls: ['./room-type.component.scss'],
})
export class RoomTypeComponent implements OnInit {
  roomTypes: RoomType[] = [];
  editingRoomType: any = null;
  newRoomType: any = {};
  showCard: boolean = false;
  page!: Page<RoomType>;
  currentPage: number = 0;
  totalPages!: number;

  @ViewChild('fileInput') fileInput!: ElementRef;

  constructor(
    private roomTypeService: RoomTypeService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.fetchRoomTypes();
  }

  fetchRoomTypes(page: number = 0, size: number = 4) {
    this.roomTypeService.getRoomType(page, size).subscribe(
      (data: any) => {
        this.page = data;
        this.roomTypes = data.content;
        this.currentPage = data.number;
        this.totalPages = data.totalPages;
      },
      (error) => {
        console.error('Errore durante il recupero dei tipi di stanza:', error);
      }
    );
  }

  nextPage() {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.fetchRoomTypes(this.currentPage);
    }
  }

  prevPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.fetchRoomTypes(this.currentPage);
    }
  }

  editRoomType(roomType: any) {
    const selectedRoomTypeId = roomType.id;
    console.log(selectedRoomTypeId);
    this.editingRoomType = { ...roomType, id: selectedRoomTypeId };
  }

  saveEditedRoomType() {
    this.roomTypeService
      .updateRoomType(this.editingRoomType.id, this.editingRoomType)
      .subscribe(
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
    this.editingRoomType = null;
  }

  addNewRoomType() {
    this.roomTypeService.createRoomType(this.newRoomType).subscribe(
      () => {
        this.newRoomType = {};
      },
      (error) => {
        console.error(
          "Errore durante l'aggiunta del nuovo tipo di stanza:",
          error
        );
      }
    );
  }

  toggleCardVisibility() {
    this.showCard = !this.showCard;
  }
  close() {
    this.showCard = false;
  }

  deleteRoomType(roomType: any) {
    const selectedRoomTypeId = roomType.id;
    this.roomTypeService.deleteRoomType(selectedRoomTypeId).subscribe(
      () => {
        this.roomTypes = this.roomTypes.filter(
          (type: any) => type.id !== selectedRoomTypeId
        );
      },
      (error) => {
        console.error(
          "Errore durante l'eliminazione del tipo di stanza:",
          error
        );
      }
    );
  }

  uploadFile(event: any) {
    const file: File = event.target.files[0];
    console.log(file);
    console.log(this.editingRoomType);

    if (file && this.editingRoomType) {
      console.log('passato');
      this.roomTypeService
        .uploadAvatar(this.editingRoomType.id, file)
        .subscribe(
          (imageUrl: string) => {
            this.editingRoomType.image = imageUrl;
            console.log(imageUrl);
          },
          (error: any) => {
            console.error("Errore durante l'upload dell'avatar:", error);
          }
        );
    }
  }
}

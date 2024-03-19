import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';

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

  @ViewChild('fileInput') fileInput!: ElementRef;

  constructor(private roomTypeService: RoomTypeService) {}

  ngOnInit(): void {
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
  onEditImageChange(event: any) {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = () => {
        const temporaryImage = reader.result as string;

        this.roomTypeService
          .uploadAvatar(this.editingRoomType.id, file)
          .subscribe(
            (imageUrl: string) => {
              this.editingRoomType.image = imageUrl;
            },
            (error: any) => {
              console.error("Errore durante l'upload dell'avatar:", error);
              this.editingRoomType.image = temporaryImage;
            }
          );
      };
      reader.readAsDataURL(file);
    }
  }

  addNewRoomType() {
    this.roomTypeService.createRoomType(this.newRoomType).subscribe(
      (response) => {
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
  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    console.log(file);
    if (file) {
      this.roomTypeService
        .uploadAvatar(this.editingRoomType.id, file)
        .subscribe(
          (imageUrl: string) => {
            this.editingRoomType.image = imageUrl;
          },
          (error: any) => {
            console.error("Errore durante l'upload dell'avatar:", error);
          }
        );
    }
  }
}

import { Component, OnInit } from '@angular/core';
import { Observable, Subject, takeUntil } from 'rxjs';

import { Accommodation } from 'src/app/models/accommodation';
import { User } from 'src/app/models/user';
import { AccommodationService } from 'src/app/services/accommodation.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-accommodation',
  templateUrl: './accommodation.component.html',
  styleUrls: ['./accommodation.component.scss'],
})
export class AccommodationComponent implements OnInit {
  accommodation: Accommodation[] = [];
  currentUser: User | undefined;
  newAccommodation: any = {};
  showCard: boolean = false;
  editingAccommodation: any = null;

  constructor(
    private userService: UserService,
    private accommodationService: AccommodationService
  ) {}

  ngOnInit(): void {
    this.getCurrentUser();
    this.fetchAccommodation();
  }

  private getCurrentUser(): void {
    this.userService.getCurrentUser().subscribe(
      (user: User) => {
        this.currentUser = user;
      },
      (error) => {
        console.log('Errore', error);
      }
    );
  }

  fetchAccommodation() {
    this.accommodationService.getAccommodation().subscribe(
      (data: Accommodation[]) => {
        this.accommodation = data;
      },
      (error) => {
        console.error('Error retrieving rooms', error);
      }
    );
  }

  addNewAccommodation(userId: string) {
    this.accommodationService
      .createAccommodation(this.newAccommodation, userId)
      .subscribe(
        (createdAccommodation) => {
          this.newAccommodation = {};
          this.accommodation.push(createdAccommodation);
          this.close();
        },
        (error) => {
          console.error('Error creating accommodation:', error);
        }
      );
  }
  toggleCardVisibility() {
    this.showCard = !this.showCard;
  }
  close() {
    this.showCard = false;
  }

  editAccommodation(accommodation: any) {
    const selectedAccommodationId = accommodation.id;
    this.editingAccommodation = {
      ...accommodation,
      id: selectedAccommodationId,
    };
  }

  saveEditedAccommodation() {
    this.accommodationService
      .updateAccommodation(
        this.editingAccommodation.id,
        this.editingAccommodation
      )
      .subscribe(
        (updatedAccommodation) => {
          this.cancelEdit();
          this.fetchAccommodation();
        },
        (error) => {
          console.error(
            "Errore durante l'aggiornamento della prenotazione:",
            error
          );
        }
      );
  }

  cancelEdit() {
    this.editingAccommodation = null;
  }

  deleteAccommodation(accommodation: any) {
    const selectedAccommodationId = accommodation.id;
    this.accommodationService
      .deleteAccommodation(selectedAccommodationId)
      .subscribe(
        () => {
          this.accommodation = this.accommodation.filter(
            (type: any) => type.id !== selectedAccommodationId
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
}

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
  accommodation!: Accommodation[];
  currentUser: User | undefined;

  private unsubscribe$: Subject<void> = new Subject<void>();

  constructor(
    private userService: UserService,
    private accommodationService: AccommodationService
  ) {}

  ngOnInit(): void {
    this.getCurrentUser();
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  private getCurrentUser(): void {
    this.userService.getCurrentUser().subscribe(
      (user: User) => {
        this.currentUser = user;

        this.saveAccommodations();
      },
      (error) => {
        console.log('Errore', error);
      }
    );
  }

  private saveAccommodations(): void {
    if (this.currentUser) {
      const userId = this.currentUser.id;
      this.accommodationService.getAccommodations(userId).subscribe(
        (savedAccommodations: Accommodation[]) => {
          console.log('Alloggi salvati con successo:', savedAccommodations);
        },
        (error) => {
          console.error('Errore durante il salvataggio degli alloggi:', error);
        }
      );
    } else {
      console.error('Utente non trovato.');
    }
  }
}

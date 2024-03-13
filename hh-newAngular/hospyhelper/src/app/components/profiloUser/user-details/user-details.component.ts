import { Component, OnInit } from '@angular/core';
import { Subject, takeUntil } from 'rxjs';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.scss'],
})
export class UserDetailsComponent implements OnInit {
  currentUser: User | undefined;
  private unsubscribe$: Subject<void> = new Subject<void>();

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.loadCurrentUser();
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  private loadCurrentUser(): void {
    this.userService
      .getCurrentUser()
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe(
        (user: User) => {
          this.currentUser = user;
          console.log(this.currentUser);
        },
        (error) => {
          console.log('Errore', error);
        }
      );
  }

  updateUserDetails(): void {
    if (this.currentUser) {
      this.userService
        .updateCurrentUser(this.currentUser.id, this.currentUser)
        .pipe(takeUntil(this.unsubscribe$))
        .subscribe(
          (updatedUser: User) => {
            this.currentUser = updatedUser;
            console.log('Dettagli utente aggiornati:', updatedUser);
          },
          (error) => {
            console.error(
              "Errore durante l'aggiornamento dei dettagli:",
              error
            );
          }
        );
    }
  }
}

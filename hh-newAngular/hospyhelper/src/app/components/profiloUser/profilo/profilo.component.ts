import { Component, OnInit, OnDestroy } from '@angular/core';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user.service';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'app-profilo',
  templateUrl: './profilo.component.html',
  styleUrls: ['./profilo.component.scss'],
})
export class ProfiloComponent implements OnInit, OnDestroy {
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

  getUserById(id: string): void {
    this.userService
      .findById(id)
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe(
        (user) => (this.currentUser = user),
        (error) => {
          console.log('Errore', error);
        }
      );
  }
}

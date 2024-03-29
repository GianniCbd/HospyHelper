import { Component, OnInit } from '@angular/core';
import { UserDto } from 'src/app/auth/user-dto';
import { AuthService } from 'src/app/auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {
  utente!: UserDto | null;
  searchTerm: string = '';

  constructor(private authSrv: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.authSrv.restore();
    this.authSrv.user$.subscribe((user) => {
      this.utente = user;
    });
  }

  logout() {
    this.authSrv.logout();
    this.router.navigate(['/login']);
  }
}

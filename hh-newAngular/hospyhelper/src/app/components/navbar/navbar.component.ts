import { Component, OnInit } from '@angular/core';
import { UserDto } from 'src/app/auth/user-dto';
import { AuthService } from 'src/app/auth/auth.service';
import { LoginRegisterDto } from 'src/app/auth/login-register-dto';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {
  utente!: UserDto | null;
  private scrollY = 0;

  constructor(private authSrv: AuthService) {}

  ngOnInit(): void {
    this.authSrv.restore();
    this.authSrv.user$.subscribe((user) => {
      this.utente = user;
    });

    window.addEventListener('scroll', () => {
      this.scrollY = window.scrollY;
    });
  }

  logout() {
    this.authSrv.logout();
  }

  getBackgroundColor(): string {
    const opacity = Math.min(1, this.scrollY / 100);
    return `rgba(169,230,233, ${opacity})`;
  }
}

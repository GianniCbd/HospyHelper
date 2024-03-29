import { Component, OnInit } from '@angular/core';
import { UserDto } from 'src/app/auth/user-dto';

@Component({
  selector: 'app-intro-page',
  templateUrl: './intro-page.component.html',
  styleUrls: ['./intro-page.component.scss'],
})
export class IntroPageComponent implements OnInit {
  utente!: UserDto | null;

  constructor() {}

  ngOnInit(): void {}
}

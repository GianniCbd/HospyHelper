import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  images: string[] = Array(49).fill(
    '../../../assets/images/HH-Photoroom.png-Photoroom.png'
  );

  constructor() {}

  ngOnInit(): void {}
}

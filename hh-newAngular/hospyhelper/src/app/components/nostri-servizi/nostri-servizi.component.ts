import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-nostri-servizi',
  templateUrl: './nostri-servizi.component.html',
  styleUrls: ['./nostri-servizi.component.scss'],
})
export class NostriServiziComponent implements OnInit {
  constructor(private router: Router) {}

  ngOnInit(): void {}
}

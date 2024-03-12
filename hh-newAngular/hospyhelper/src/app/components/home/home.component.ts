import { Component, OnInit } from '@angular/core';
import { Post } from 'src/app/models/post';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  images: string[] = Array(49).fill(
    '../../../assets/images/HH-Photoroom.png-Photoroom.png'
  );

  post: Post[] | undefined;

  constructor(private postSrv: PostService) {}

  ngOnInit(): void {}
}

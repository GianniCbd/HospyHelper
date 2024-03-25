import { Component, OnInit } from '@angular/core';
import { Page } from 'src/app/models/page';
import { Post } from 'src/app/models/post';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss'],
})
export class PostComponent implements OnInit {
  posts: Post[] = [];
  editingPost: any = null;
  newPost: any = {};
  showCard: boolean = false;
  page!: Page<Post>;
  currentPage: number = 0;
  totalPages!: number;

  constructor(private postSrv: PostService) {}

  ngOnInit(): void {
    this.fetchPost();
  }

  fetchPost(page: number = 0, size: number = 4) {
    this.postSrv.getPost(page, size).subscribe(
      (data: any) => {
        this.page = data;
        this.posts = data.content;
        this.currentPage = data.number;
        this.totalPages = data.totalPages;
      },
      (error) => {
        console.error('Errore durante il recupero dei tipi di stanza:', error);
      }
    );
  }

  nextPage() {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.fetchPost(this.currentPage);
    }
  }

  prevPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.fetchPost(this.currentPage);
    }
  }

  editPost(post: any) {
    const selectedPostId = post.id;
    console.log(selectedPostId);
    this.editingPost = { ...post, id: selectedPostId };
  }

  saveEditedPost() {
    this.postSrv.updatePost(this.editingPost.id, this.editingPost).subscribe(
      (updatedPost) => {
        console.log('Post aggiornata con successo:', updatedPost);
        this.cancelEdit();
      },
      (error) => {
        console.error("Errore durante l'aggiornamento della stanza:", error);
      }
    );
  }
  cancelEdit() {
    this.editingPost = null;
  }

  addNewPost() {
    this.postSrv.savePost(this.newPost).subscribe(
      () => {
        this.newPost = {};
      },
      (error) => {
        console.error(
          "Errore durante l'aggiunta del nuovo tipo di stanza:",
          error
        );
      }
    );
  }

  toggleCardVisibility() {
    this.showCard = !this.showCard;
  }
  close() {
    this.showCard = false;
  }

  deletePost(post: any) {
    const selectedPostId = post.id;
    this.postSrv.deletePost(selectedPostId).subscribe(
      () => {
        this.posts = this.posts.filter(
          (type: any) => type.id !== selectedPostId
        );
      },
      (error) => {
        console.error("Errore durante l'eliminazione del tpost:", error);
      }
    );
  }
}

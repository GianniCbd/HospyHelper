import { Component, OnInit } from '@angular/core';
import { Page } from 'src/app/models/page';
import { Post } from 'src/app/models/post';
import { PostService } from 'src/app/services/post.service';
import { UserService } from 'src/app/services/user.service';

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

  constructor(private postSrv: PostService, private userService: UserService) {}

  ngOnInit(): void {
    this.fetchPost();
  }

  fetchPost(page: number = 0, size: number = 4) {
    this.postSrv.getPost(page, size).subscribe(
      (data: any) => {
        this.page = data;
        this.posts = data.content.map((post: Post) => ({
          ...post,
          likeClicked: false,
          dislikeClicked: false,
        }));
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

  viewPostLikes(post: Post) {
    this.incrementLikes(post.id);
  }

  decrementLikes(postId: number) {
    this.postSrv.decrementLikes(postId).subscribe(
      () => {
        console.log('Likes decrementati per il post con ID:', postId);
        this.incrementViews(postId);
      },
      (error) => {
        console.error('Errore durante il decremento dei likes del post', error);
      }
    );
  }
  incrementLikes(postId: number) {
    this.postSrv.incrementLikes(postId).subscribe(
      () => {
        console.log('Likes incrementati per il post con ID:', postId);
        this.incrementViews(postId);
      },
      (error) => {
        console.error("Errore durante l'incremento dei likes del post:", error);
      }
    );
  }

  viewPostDetails(post: Post) {
    this.incrementViews(post.id);
  }

  incrementViews(postId: number) {
    this.postSrv.incrementViews(postId).subscribe(
      () => {
        console.log('Visualizzazione incrementata per il post con ID:', postId);
      },
      (error) => {
        console.error(
          "Errore durante l'incremento delle visualizzazioni del post:",
          error
        );
      }
    );
  }
  ViewPostShares(post: Post) {
    this.incrementShares(post.id);
  }

  incrementShares(postId: number) {
    this.postSrv.incrementShares(postId).subscribe(
      () => {
        console.log('Visualizzazione incrementata per il post con ID:', postId);
        this.incrementViews(postId);
      },
      (error) => {
        console.error(
          "Errore durante l'incremento delle visualizzazioni del post:",
          error
        );
      }
    );
  }

  editPost(post: any) {
    const selectedPostId = post.id;
    console.log(selectedPostId);
    console.log(post);

    this.editingPost = { ...post, id: selectedPostId };
  }

  saveEditedPost() {
    this.userService.getCurrentUser().subscribe(
      (currentUser) => {
        if (currentUser && currentUser.id === this.editingPost.user.id) {
          this.postSrv
            .updatePost(this.editingPost.id, this.editingPost)
            .subscribe(
              (updatedPost) => {
                console.log('Post aggiornata con successo:', updatedPost);
                this.cancelEdit();
              },
              (error) => {
                console.error(
                  "Errore durante l'aggiornamento della stanza:",
                  error
                );
              }
            );
        } else {
          alert('Non sei autorizzato a modificare questo post.');
        }
      },
      (error) => {
        console.error(
          "Errore durante il recupero dell'utente corrente:",
          error
        );
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
    this.userService.getCurrentUser().subscribe(
      (currentUser) => {
        if (currentUser && currentUser.id === post.user.id) {
          this.postSrv.deletePost(selectedPostId).subscribe(
            () => {
              this.posts = this.posts.filter(
                (p: any) => p.id !== selectedPostId
              );
              console.log('Post eliminato con successo');
            },
            (error) => {
              console.error("Errore durante l'eliminazione del post:", error);
            }
          );
        } else {
          console.error('Non sei autorizzato a eliminare questo post.');
        }
      },
      (error) => {
        console.error(
          "Errore durante il recupero dell'utente corrente:",
          error
        );
      }
    );
  }
}

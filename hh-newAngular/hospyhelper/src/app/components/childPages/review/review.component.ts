import { Component, OnInit } from '@angular/core';
import { Review } from 'src/app/models/review';
import { User } from 'src/app/models/user';
import { ReviewService } from 'src/app/services/review.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.scss'],
})
export class ReviewComponent implements OnInit {
  reviews: Review[] = [];

  newReview: any = {};
  editingReview: any = null;
  showCard: boolean = false;
  constructor(private reviewSrv: ReviewService, private UserSrv: UserService) {}

  ngOnInit(): void {
    this.fetchReviews();
  }

  getStarsArray(rating: number): boolean[] {
    const starsArray = [];
    for (let i = 1; i <= 5; i++) {
      starsArray.push(i <= rating);
    }
    return starsArray;
  }

  fetchReviews() {
    this.reviewSrv.getAllReviews().subscribe(
      (data) => {
        this.reviews = data.content;
      },
      (error) => {
        console.error('Errore durante il recupero dei tipi di stanza:', error);
      }
    );
  }

  addNewReview() {
    this.reviewSrv.saveReview(this.newReview).subscribe(
      (response) => {
        this.reviews.push(response);
        this.newReview = {};
        this.showCard = false;
      },
      (error) => {
        console.error("Errore durante l'aggiunta del dipendente:", error);
      }
    );
  }
  toggleCardVisibility() {
    this.showCard = !this.showCard;
  }
  close() {
    this.showCard = false;
  }

  editReview(reviews: any) {
    if (reviews && reviews.id) {
      const selectedReviewId = reviews.id;
      console.log(selectedReviewId);

      this.editingReview = { ...reviews, id: selectedReviewId };
    } else {
      console.error('Errore: recensione non valida.');
    }
  }
  saveEditedReview() {
    this.UserSrv.getCurrentUser().subscribe(
      (currentUser) => {
        console.log(currentUser);

        if (currentUser && currentUser.id === this.editingReview.user.id) {
          this.reviewSrv
            .updateReview(this.editingReview.id, this.editingReview)
            .subscribe(
              (updatedre) => {
                console.log('Recensione aggiornata con successo:', updatedre);
                this.cancelEdit();
                this.fetchReviews();
              },
              (error) => {
                console.error(
                  "Errore durante l'aggiornamento della recensione:",
                  error
                );
              }
            );
        } else {
          alert('Non sei autorizzato a modificare questa recensione.');
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
    this.editingReview = null;
  }

  deleteReview(review: any) {
    const selectedReviewId = review.id;

    this.UserSrv.getCurrentUser().subscribe(
      (currentUser) => {
        if (currentUser && currentUser.id === review.user.id) {
          this.reviewSrv.deleteReview(selectedReviewId).subscribe(
            () => {
              this.reviews = this.reviews.filter(
                (p: any) => p.id !== selectedReviewId
              );
              console.log('Post eliminato con successo');
            },
            (error) => {
              console.error("Errore durante l'eliminazione del post:", error);
            }
          );
        } else {
          alert('Non sei autorizzato a eliminare questo post.');
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

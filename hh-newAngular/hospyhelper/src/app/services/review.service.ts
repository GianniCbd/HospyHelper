import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Review } from '../models/review';
import { Page } from '../models/page';

@Injectable({
  providedIn: 'root',
})
export class ReviewService {
  apiUrl: string = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getAllReviews(
    page: number = 0,
    size: number = 20,
    orderBy: string = 'id'
  ): Observable<Page<Review>> {
    const params = new HttpParams()
      .set('pageNumber', page.toString())
      .set('pageSize', size.toString())
      .set('orderBy', orderBy);
    return this.http.get<Page<Review>>(`${this.apiUrl}/reviews`, { params });
  }

  saveReview(review: Review): Observable<Review> {
    return this.http.post<Review>(`${this.apiUrl}/reviews/save`, review);
  }

  updateReview(id: number, review: Review): Observable<Review> {
    return this.http.put<Review>(`${this.apiUrl}/reviews/${id}`, review);
  }

  deleteReview(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/reviews/${id}`);
  }

  findReviewsWithRatingGreaterThan(rating: number): Observable<Review[]> {
    return this.http.get<Review[]>(
      `${this.apiUrl}/reviews/findByRatingGreaterThan?rating=${rating}`
    );
  }
}

import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Post } from '../models/post';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { Page } from '../models/page';

@Injectable({
  providedIn: 'root',
})
export class PostService {
  apiUrl: string = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getPost(
    pageNumber: number = 0,
    pageSize: number = 4,
    orderBy: string = 'title'
  ): Observable<Page<Post>> {
    const params = new HttpParams()
      .set('pageNumber', pageNumber.toString())
      .set('pageSize', pageSize.toString())
      .set('orderBy', orderBy);

    return this.http.get<Page<Post>>(`${this.apiUrl}/post`, {
      params,
    });
  }

  savePost(postDTO: Post): Observable<Post> {
    return this.http.post<Post>(`${this.apiUrl}/post/save`, postDTO);
  }

  getPostById(id: number): Observable<Post> {
    return this.http.get<Post>(`${this.apiUrl}/post/${id}`);
  }

  updatePost(id: number, postDTO: Post): Observable<Post> {
    return this.http.put<Post>(`${this.apiUrl}/post/${id}`, postDTO);
  }

  deletePost(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/post/${id}`);
  }

  searchPostsByTitle(title: string): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.apiUrl}/post/search?title=${title}`);
  }

  getAllPostsOrderByLikesDesc(): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.apiUrl}/post/likes-desc`);
  }

  getRecentPosts(): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.apiUrl}/post/recent`);
  }

  incrementViews(postId: number): Observable<void> {
    return this.http.put<void>(
      `${this.apiUrl}/post/${postId}/increment-views`,
      {}
    );
  }

  incrementLikes(postId: number): Observable<void> {
    return this.http.put<void>(
      `${this.apiUrl}/post/${postId}/increment-likes`,
      {}
    );
  }
  decrementLikes(postId: number): Observable<void> {
    return this.http.put<void>(
      `${this.apiUrl}/post/${postId}/decrement-likes`,
      {}
    );
  }

  incrementShares(postId: number): Observable<void> {
    return this.http.put<void>(
      `${this.apiUrl}/post/${postId}/increment-shares`,
      {}
    );
  }
}

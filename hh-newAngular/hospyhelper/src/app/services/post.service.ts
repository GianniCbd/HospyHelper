import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Post } from '../models/post';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PostService {
  apiUrl: string = environment.apiUrl;

  constructor(private http: HttpClient) {}

  savePost(postDTO: Post, userId: string): Observable<Post> {
    return this.http.post<Post>(`${this.apiUrl}/save/${userId}`, postDTO);
  }

  getPostById(id: number): Observable<Post> {
    return this.http.get<Post>(`${this.apiUrl}/${id}`);
  }

  updatePost(id: number, postDTO: Post): Observable<Post> {
    return this.http.put<Post>(`${this.apiUrl}/${id}`, postDTO);
  }

  deletePost(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  searchPostsByTitle(title: string): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.apiUrl}/search?title=${title}`);
  }

  getAllPostsOrderByLikesDesc(): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.apiUrl}/likes-desc`);
  }

  getRecentPosts(): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.apiUrl}/recent`);
  }

  incrementViews(postId: number): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/${postId}/increment-views`, {});
  }
}

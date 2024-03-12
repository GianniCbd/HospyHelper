import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
} from '@angular/common/http';
import { Observable, take, switchMap } from 'rxjs';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {
  constructor() {}

  newReq!: HttpRequest<any>;

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    return next.handle(request).pipe(
      take(1),
      switchMap(() => {
        const token: any = localStorage.getItem('user');

        if (token) {
          const accessToken = JSON.parse(token)?.accessToken;
          console.log(accessToken);

          if (accessToken) {
            this.newReq = request.clone({
              setHeaders: {
                Authorization: `Bearer ${accessToken}`,
              },
            });
          } else {
            this.newReq = request;
          }
        } else {
          this.newReq = request;
        }

        return next.handle(this.newReq);
      })
    );
  }
}

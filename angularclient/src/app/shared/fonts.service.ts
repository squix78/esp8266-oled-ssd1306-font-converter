import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, retry, throwError } from 'rxjs';
import { Font } from './font';
import { PixelFont } from './pixel-font';

@Injectable({
  providedIn: 'root'
})
export class FontsService {

  baseUrl = "/rest";

  constructor(private http: HttpClient) { }

  findAll(): Observable<Font[]> {
    return this.http
      .get<Font[]>(this.baseUrl + '/fonts')
      .pipe(retry(1), catchError(this.handleError));
  }

  convertToPixelFont(pixelFont: PixelFont): Observable<PixelFont> {

    return this.http
      .post<PixelFont>(this.baseUrl + '/fontArray', pixelFont)
      .pipe(retry(1), catchError(this.handleError));
  }

  // Error handling
  handleError(error: any) {
      let errorMessage = '';
      if (error.error instanceof ErrorEvent) {
        // Get client-side error
        errorMessage = error.error.message;
      } else {
        // Get server-side error
        errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
      }
      window.alert(errorMessage);
      return throwError(() => {
        return errorMessage;
      });
    }
}

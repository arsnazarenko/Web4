import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {Point} from '../model/point';
import {catchError, retry} from 'rxjs/operators';
import {PointRequest} from '../model/pointRequest';


@Injectable({
  providedIn: 'root'
})

export class PointService {
  private HTTP_OPTIONS = {
    headers: new HttpHeaders({'Content-Type': 'application/json', Authorization: 'Bearer ' + localStorage.getItem('auth-token')})
  };

  readonly REST_API_URL = 'http://localhost:8080/point';


  constructor(private httpClient: HttpClient) {
  }


  public getPoints(): Observable<Point[]> {
    return this.httpClient.get<Point[]>(this.REST_API_URL, this.HTTP_OPTIONS)
      .pipe(
        retry(1),
        catchError(this.handleError)
      );
  }

  public addPoint(pointReq: PointRequest): Observable<Point> {
    return this.httpClient.post<Point>(this.REST_API_URL, pointReq, this.HTTP_OPTIONS)
      .pipe(
      catchError(this.handleError)
    );
  }


  private handleError(error: HttpErrorResponse): Observable<any> {
    if (error.error instanceof ErrorEvent) {

      console.error('An error occurred:', error.error.message);
    } else {

      console.error(
        `Backend returned code ${error.status}, ` +
        `Body: ${error.error}`);
    }
    return throwError(
      'Something bad happened; please try again later.');
  }

}

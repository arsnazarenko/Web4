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
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  readonly REST_API_URL = 'http://localhost:8080/point';
  readonly REST_API_AUTH = 'http://localhost:8080/auth';
  readonly REST_API_REG = 'http://localhost:8080/register';

  constructor(private httpClient: HttpClient) {
  }

  public login(log: string, passwd: string): Observable<any> {
    return this.httpClient.post<any>(this.REST_API_AUTH, {login: log, password: passwd}, this.HTTP_OPTIONS)
      .pipe(
        catchError(this.handleError)
      );
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

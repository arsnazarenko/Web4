import { Injectable } from '@angular/core';
import {User} from '../model/user';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {tap} from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private HTTP_OPTIONS = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };
  readonly REST_API_AUTH = 'http://localhost:8080/auth';
  readonly REST_API_REG = 'http://localhost:8080/register';

  private token: string = null;

  constructor(private http: HttpClient) { }

  public login(user: User): Observable<{token: string}> {
    return this.http.post<{token: string}>(this.REST_API_AUTH, user, this.HTTP_OPTIONS)
      .pipe(
        tap(
          ({token}) => {
            localStorage.setItem('auth-token', token);
            this.setToken(token);
          }
        )
      );
  }

  public setToken(token: string): void {
    this.token = token;
  }

  public getToken(): string {
    return this.token;
  }

  public isAuthenticated(): boolean {
    return !!this.token;
  }

  public logaout(): void {
    this.setToken(null);
    localStorage.clear();
  }

  public register(user: User): Observable<{login: string, status: string}>{
    return this.http.post<{login: string, status: string}>(this.REST_API_REG, user, this.HTTP_OPTIONS);
  }
}

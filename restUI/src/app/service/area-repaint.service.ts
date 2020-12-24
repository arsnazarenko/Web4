import { Injectable } from '@angular/core';
import {Observable, Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AreaRepaintService {
  constructor() { }
  private subject = new Subject<number>();

  sendMessage(radiusValue: number): void {
    this.subject.next();
  }

  getMessage(): Observable<number> {
    return this.subject.asObservable();
  }
}

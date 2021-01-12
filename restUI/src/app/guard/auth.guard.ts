import {ActivatedRouteSnapshot, CanActivate, CanActivateChild, Router, RouterStateSnapshot} from '@angular/router';
import {Observable, of} from 'rxjs';
import {Injectable} from '@angular/core';
import {AuthService} from '../service/auth.service';
@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private auth: AuthService,
              private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    if (this.auth.isAuthenticated()) {
      return of(true);
    } else {
      this.router.navigate(['/login']);
      return of(false);
    }
  }
}

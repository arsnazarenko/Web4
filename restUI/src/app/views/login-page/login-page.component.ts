import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../service/auth.service';
import {Subscription} from 'rxjs';
import {ActivatedRoute, Params, Router} from '@angular/router';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit, OnDestroy {
  loginForm: FormGroup;
  private authSubscription: Subscription;

  constructor(private auth: AuthService,
              private router: Router,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.loginForm = new FormGroup( {
      login: new FormControl(null, [Validators.required, Validators.minLength(6)]),
      password: new FormControl(null, [Validators.required, Validators.minLength(6)])
    });
  }

  public onSubmit(): void {
    this.loginForm.disable();

    this.authSubscription = this.auth.login(this.loginForm.value).subscribe(
      () => this.router.navigate(['/main']),
      error => {
        console.warn(error);
        this.loginForm.enable();
      }
    );
  }

  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }

}

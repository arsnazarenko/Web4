import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../service/auth.service';
import {Router} from '@angular/router';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.css']
})
export class RegisterPageComponent implements OnInit, OnDestroy {
  regForm: FormGroup;
  private authSub: Subscription;

  constructor(private auth: AuthService,
              private router: Router) { }

  ngOnInit(): void {
    this.regForm = new FormGroup( {
      login: new FormControl(null, [Validators.required, Validators.minLength(6)]),
      password: new FormControl(null, [Validators.required, Validators.minLength(6)])
    });
  }


  public onSubmit(): void {
    this.regForm.disable();
    this.authSub = this.auth.register(this.regForm.value).subscribe(
      () => {
        this.router.navigate(['/login']);
      },
      error => {
        console.warn(error);
        this.regForm.enable();
      }
    );
  }

  ngOnDestroy(): void {
    if (this.authSub) {
      this.authSub.unsubscribe();
    }
  }
}

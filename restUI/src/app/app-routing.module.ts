import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {MainPageComponent} from './views/main-page/main-page.component';
import {LoginPageComponent} from './views/login-page/login-page.component';
import {NotFoundPageComponent} from './views/not-found-page/not-found-page.component';
import {RegisterPageComponent} from './views/register-page/register-page.component';
import {AuthGuard} from './guard/auth.guard';


const routes: Routes = [

  {path: 'main', component: MainPageComponent, canActivate: [AuthGuard]},
  {path: 'login', component: LoginPageComponent},
  {path: '', redirectTo: '/login', pathMatch: 'full'},
  {path: 'register', component: RegisterPageComponent},
  {path: '**', component: NotFoundPageComponent},
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule {
}

import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {PointDataComponent} from './views/point-data/point-data.component';
import {PointFormComponent} from './views/point-form/point-form.component';
import {MainPageComponent} from './views/main-page/main-page.component';
import {LoginPageComponent} from './views/login-page/login-page.component';
import {NotFoundPageComponent} from './views/not-found-page/not-found-page.component';


const routes: Routes = [
  {path: 'main', component: MainPageComponent},
  {path: 'login', component: LoginPageComponent},
  {path: '**', component: NotFoundPageComponent},
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

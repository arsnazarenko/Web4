import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {PointDataComponent} from './views/point-data/point-data.component';
import {PointFormComponent} from './views/point-form/point-form.component';


const routes: Routes = [
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

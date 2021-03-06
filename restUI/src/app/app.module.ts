import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import {HttpClientModule} from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { PointDataComponent } from './views/point-data/point-data.component';
import {TableModule} from 'primeng/table';
import { PointFormComponent } from './views/point-form/point-form.component';
import {SliderModule} from 'primeng/slider';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { ContentComponent } from './views/content/content.component';
import { PointGraphComponent } from './views/point-graph/point-graph.component';
import { MainPageComponent } from './views/main-page/main-page.component';
import { LoginPageComponent } from './views/login-page/login-page.component';
import { NotFoundPageComponent } from './views/not-found-page/not-found-page.component';
import { RegisterPageComponent } from './views/register-page/register-page.component';

@NgModule({
  declarations: [
    AppComponent,
    PointDataComponent,
    PointFormComponent,
    ContentComponent,
    PointGraphComponent,
    MainPageComponent,
    LoginPageComponent,
    NotFoundPageComponent,
    RegisterPageComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    TableModule,
    SliderModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

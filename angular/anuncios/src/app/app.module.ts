import { BrowserModule } from '@angular/platform-browser';
import { NgModule,LOCALE_ID } from '@angular/core';

import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { DirectivaComponent } from './directiva/directiva.component';
import { AnunciosComponent } from './anuncios/anuncios.component';
import { FormComponent } from './anuncios/form.component';
import { PaginatorComponent } from './paginator/paginator.component';
import { AnuncioService } from './anuncios/anuncio.service';
import { RouterModule, Routes} from '@angular/router';
import { registerLocaleData } from '@angular/common';
import localeES from '@angular/common/locales/es';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { DetalleComponent } from './anuncios/detalle/detalle.component';


registerLocaleData(localeES, 'es');

const routes: Routes= [
  {path: '', redirectTo: '/anuncios', pathMatch: 'full'},
  {path: 'directivas', component: DirectivaComponent},
  {path: 'anuncios', component: AnunciosComponent},
  {path: 'anuncios/page/:page', component: AnunciosComponent},
  {path: 'anuncios/form', component: FormComponent},
  {path: 'anuncios/form/:id', component: FormComponent}
  // {path: 'anuncios/ver/:id', component: DetalleComponent}
];

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    DirectivaComponent,
    AnunciosComponent,
    FormComponent,
    PaginatorComponent,
    DetalleComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(routes),
    BrowserAnimationsModule,
    MatDatepickerModule, MatNativeDateModule

  ],
  providers: [AnuncioService,
  {provide: LOCALE_ID, useValue: 'es' }],
  bootstrap: [AppComponent]
})
export class AppModule { }

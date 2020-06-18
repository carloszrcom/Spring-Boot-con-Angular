import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  h1 = 'Esta página es el index';
  title = 'Primera app Angular';
  subtitulo: string = 'Desarrollo web';
}

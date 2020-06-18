import { Component } from '@angular/core';

@Component({
  selector: 'app-directiva',
  templateUrl: './directiva.component.html'
})
export class DirectivaComponent {

  listaCoches: string[] = ['Renault', 'Ford', 'Seat', 'Toyota', 'Citroen'];
  habilitar: boolean = true;

  constructor() { }

  setHabilitar(): void {
    this.habilitar = (this.habilitar==true)? false: true;
  }
}

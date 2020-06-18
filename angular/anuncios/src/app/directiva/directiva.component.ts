import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-directiva',
  templateUrl: './directiva.component.html',
  styleUrls: ['./directiva.component.css']
})
export class DirectivaComponent {

  listaAnuncios: string[] = ['Anuncio1', 'Anuncio2', 'Anuncio3'];
  habilitar: boolean = true;

  constructor() { }

  setHabilitar(): void {
    this.habilitar = (this.habilitar == true)? false: true;
  }

  // En lugar de esta función, se puede poner el siguiente código en el texto del botón directamente
  //habilitar == true? 'Ocultar': 'Mostrar'
  setTextoBoton(): string {
    let texto: string = "";
    if(this.habilitar == true) {
      texto = 'Ocultar';
    } else {
      texto = 'Mostrar';
    }
    return texto;
  }

  ngOnInit(): void {
  }

}

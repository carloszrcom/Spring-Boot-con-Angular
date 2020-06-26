import { Component, OnInit } from '@angular/core';
import { Anuncio } from '../anuncio';
import { AnuncioService } from '../anuncio.service';
import { ActivatedRoute } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'detalle-anuncio',
  templateUrl: './detalle.component.html',
  styleUrls: ['./detalle.component.css']
})
export class DetalleComponent implements OnInit {

  anuncio: Anuncio;
  tituloPagina: string = "Detalle del anuncio";
  private fotoSeleccionada: File;

  constructor(private anuncioService: AnuncioService, private activatedRoute: ActivatedRoute) { // Necesitamos ActivatedRoute cuando cambia un parámetro del id
   }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe( // Vamos a suscribir cuando cambia el parámetro del id
      params => {
        let id: number = +params.get('id'); // Con el + convertimos el parámetro a tipo number
        if(id) {
          this.anuncioService.getAnunio(id).subscribe(
            anuncio => {
              this.anuncio = anuncio;
            }
          );
        }
      }
    );
  }

  seleccionarFoto(event) {
    this.fotoSeleccionada = event.target.files[0];
    console.log(this.fotoSeleccionada);
  }

  subirFoto() {
    this.anuncioService.subirFoto(this.fotoSeleccionada, this.anuncio.id)
      .subscribe(
        anuncio => {
          this.anuncio = anuncio;
          Swal.fire(
            '¡Foto subida!',
            `La foto ${this.anuncio.foto} se ha subido con éxito`,
            'success'
          );

        }
      );
  }
}

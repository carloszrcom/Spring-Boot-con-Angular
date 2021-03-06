import { Component, OnInit, Input } from '@angular/core';
import { HttpEventType } from '@angular/common/http';
import { Anuncio } from '../anuncio';
import { AnuncioService } from '../anuncio.service';
import { ModalService } from './modal.service';
// import { ActivatedRoute } from '@angular/router'; // Lo hemos sustituido por ModalService

import Swal from 'sweetalert2';

@Component({
  selector: 'detalle-anuncio',
  templateUrl: './detalle.component.html',
  styleUrls: ['./detalle.component.css']
})
export class DetalleComponent implements OnInit {

  @Input() anuncio: Anuncio;
  tituloPagina: string = "Detalle del anuncio";
  fotoSeleccionada: File;
  progreso: number = 0; // Progreso de subida de la foto

  constructor(
    private anuncioService: AnuncioService,
    public modalService: ModalService
    // private activatedRoute: ActivatedRoute  // Necesitamos ActivatedRoute cuando cambia un parámetro del id
  ) {
   }

  ngOnInit(): void { // Prescindimos del siguiente código por inyectar el anuncio mediante Input
    // this.activatedRoute.paramMap.subscribe( // Vamos a suscribir cuando cambia el parámetro del id
    //   params => {
    //     let id: number = +params.get('id'); // Con el + convertimos el parámetro a tipo number
    //     if(id) {
    //       this.anuncioService.getAnunio(id).subscribe(
    //         anuncio => {
    //           this.anuncio = anuncio;
    //         }
    //       );
    //     }
    //   }
    // );
  }

  seleccionarFoto(event) {
    this.fotoSeleccionada = event.target.files[0];
    this.progreso = 0;
    console.log(this.fotoSeleccionada);
    if(this.fotoSeleccionada.type.indexOf('image') < 0) {    // Validar que el tipo de archivo seleccionado es una foto
      Swal.fire('Error seleccionar imagen', `El archivo debe ser de tipo imagen`, 'error');
      this.fotoSeleccionada = null;
    }
  }

  subirFoto() {

    if(!this.fotoSeleccionada) {  // Validar si existe una foto para subir
      Swal.fire(
        'Error Upload',
        `Debe seleccionar una foto.`,
        'error'
      );
    } else {
      this.anuncioService.subirFoto(this.fotoSeleccionada, this.anuncio.id)
        .subscribe(
          event => {
            // this.anuncio = anuncio;
            if(event.type === HttpEventType.UploadProgress) {   // === significa idéntico (por valor y tipo de dato)
              this.progreso = Math.round((event.loaded/event.total) * 100);
            } else if(event.type === HttpEventType.Response) {
              let response: any = event.body;
              this.anuncio = response.anuncio as Anuncio;

              // Cada vez que subamos una foto tenemos que notificar a nuestros observadores
              this.modalService.notificarUpload.emit(this.anuncio);
              Swal.fire('¡Foto subida!', response.mensaje, 'success');
            }
          }
        );
    }
  }

  cerrarModal() {
    this.modalService.cerrarModal();
    this.fotoSeleccionada = null;
    this.progreso = 0;
  }

}

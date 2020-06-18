import { Component, OnInit } from '@angular/core';
import { Anuncio } from './anuncio';
import { AnuncioService } from './anuncio.service';
import Swal from 'sweetalert2';
import { tap } from 'rxjs/operators';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-anuncios',
  templateUrl: './anuncios.component.html',
  styleUrls: ['./anuncios.component.css']
})
export class AnunciosComponent implements OnInit {

  anuncios: Anuncio[];
  constructor(
    private anuncioService: AnuncioService, private activatedRoute: ActivatedRoute) { } // ActivatedRoute para indicar que 'page' puede cambiar

  ngOnInit(): void {

    this.activatedRoute.paramMap.subscribe(
      params => {
        let page: number = +params.get('page'); // Añadiendo '+' convertimos el string en number

        if(!page) {
          page = 0;
        }
        
        this.anuncioService.getAnuncios(page)
          .pipe(
            tap(
              response => {
                // this.anuncios = anuncios;
                console.log('AnuncioComponent: tap 3');
                (response.content as Anuncio[]).forEach(
                  anuncio => {
                    console.log(anuncio.titulo);
                  }
                );
              }
            )
          )
          .subscribe(response => this.anuncios = response.content as Anuncio[]);  // 'anuncios' viene de la conversión realizada en el 'map' de anuncio.service.ts
      }
    );
  }

  // Borrar Anuncio
  delete(anuncio: Anuncio): void {
    Swal.fire({
      title: '¿Está seguro?',
      text: `¿Seguro que desea eliminar el anuncio ${anuncio.titulo}?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, eliminar!'
    }).then((result) => {
      if (result.value) {
        // Añadimos la línea para eliminar
        this.anuncioService.delete(anuncio.id).subscribe(
          response => {
            this.anuncios = this.anuncios.filter(anu => anu !== anuncio); // Quita de la lista el anuncio eliminado
            Swal.fire(
              'Anuncio eliminado!',
              `Anuncio ${anuncio.titulo} eliminado con éxito`,
              'success'
            )
          }
        );
      }
    })
  }

}

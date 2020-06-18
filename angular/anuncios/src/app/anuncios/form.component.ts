import { Component, OnInit } from '@angular/core';
import { Anuncio } from './anuncio';
import { AnuncioService } from './anuncio.service';
import { Router, ActivatedRoute } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FormComponent implements OnInit {

  public anuncio: Anuncio = new Anuncio();
  public tituloPagina: string = "Crear Anuncio";
  public errores: string[];

  constructor(
    private anuncioService: AnuncioService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.cargarAnuncio();
  }

  // Carga un anuncio
  cargarAnuncio(): void {
    this.activatedRoute.params.subscribe(
      params => {
        let id = params['id'];
        if(id) {
          this.anuncioService.getAnunio(id).subscribe( (anuncio) => this.anuncio = anuncio );
        }
      }
    );
  }

  // Crea un anuncio
  create(): void {
    console.log("¡Clicado!");
    console.log(this.anuncio);
    this.anuncioService.create(this.anuncio).subscribe(
      anuncio => {
        this.router.navigate(['/anuncios']); // Redirige al listado de anuncios
        Swal.fire('Nuevo anuncio', `Anuncio ${anuncio.titulo} creado con éxito.`, 'success');
      },
      err => {
        this.errores = err.error.errors as string[];
        console.error('Código de error desde el backend: ' + err.status);
        console.error(err.error.errors);
      }
    );
  }

  // Actualizar un anuncio
  update(): void {
    this.anuncioService.update(this.anuncio).subscribe(
      json => {
        this.router.navigate(['/anuncios']);
        Swal.fire('Anuncio actualizado', `${json.mensaje} Título: ${json.anuncio.titulo}`, 'success');
      },
      err => {
        this.errores = err.error.errors as string[];
        console.error('Código de error desde el backend: ' + err.status);
        console.error(err.error.errors);
      }
    );
  }
}

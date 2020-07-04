import { Component, OnInit } from '@angular/core';
import { Anuncio } from './anuncio';
import { Region } from './region';
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
  regiones: Region[];

  constructor(
    private anuncioService: AnuncioService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) { }

  ngOnInit(): void {
    // Suscribir observador para editar un anuncio
    this.cargarAnuncio();

    // Suscribirnos al listado de regiones, para poblar la lista desplegable 'select'.
    this.anuncioService.getRegiones().subscribe(regiones => {
      this.regiones = regiones;
    });
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

  // Compara las los objetos region para que quede seleccionada la región origen en el formulario al editar un anuncio
  compararRegion(o1: Region, o2: Region): boolean {
    if (o1 === undefined && o2 === undefined) {
      return true;
    }
   
    return o1 === null || o2 === null || o1 === undefined || o2 === undefined ? false : o1.id === o2.id;
  }
}

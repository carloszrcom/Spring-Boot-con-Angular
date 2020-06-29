import { Injectable } from '@angular/core';
import { Anuncio } from './anuncio';
import { Observable, of, throwError } from 'rxjs';
import { HttpClient, HttpHeaders, HttpRequest, HttpEvent} from '@angular/common/http';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';
import { formatDate, DatePipe } from '@angular/common';
//import { ANUNCIOS } from './anuncios.json';
//import { of } from 'rxjs';
import { map, catchError, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AnuncioService {

  private urlEndPoint: string = 'http://localhost:8080/api/anuncios';
  private httpHeaders = new HttpHeaders({'Content-type': 'application/json'});

  constructor(private http: HttpClient, private router: Router) { }


  // Obtiene todos los anuncios (paginado)
  getAnuncios(page: number): Observable<any> {
    return this.http.get(this.urlEndPoint + '/page/' + page).pipe(
      tap(
        (response: any) => {
          console.log('ClienteService: tap 1');
          (response.content as Anuncio[]).forEach(
            anuncio => {
              console.log(anuncio.titulo);
            }
          )
        }
      ),
      map((response: any) => {
        (response.content as Anuncio[]).map(
          anuncio => {
            anuncio.titulo = anuncio.titulo.toUpperCase();   // Para mostrar el título en la vista en MAYÚSCULAS
            // EL FORMATO ESPAÑOL SE HA CONFIGURADO A NIVEL GLOBAL DE LA APP EN APP.MODULE.TS
            // anuncio.createAt = formatDate(anuncio.createAt, 'dd-MM-yyyy', 'en-US'); // Para ver la fecha en formato dd-MM-
            // let datePipe = new DatePipe('en-US'); // Otra forma de cambiar el formato  de la fecha (esta línea y la siguiente)
            // anuncio.createAt = datePipe.transform(anuncio.createAt, 'dd/MM/yyyy');
            let datePipe = new DatePipe('es');
            // anuncio.createAt = datePipe.transform(anuncio.createAt, 'fullDate'); // También se puede formatear la fecha en la plantilla(se procede a ello)
            return anuncio;
          });
          return response;
      }),
      tap(
        response => {
          console.log('ClienteService: tap 2');
          (response.content as Anuncio[]).forEach(
            anuncio => {
              console.log(anuncio.titulo);
            }
          )
        }
      )
    );
  }

  // Crear Anuncio
  create(anuncio: Anuncio): Observable<Anuncio> {
    return this.http.post(this.urlEndPoint, anuncio, {headers: this.httpHeaders}).pipe(
      map((response: any) => response.anuncio as Anuncio),
      catchError(e => {

        if(e.status == 400) {
          return throwError(e);
        }

        console.error('Error al crear. ' + e.error.mensaje);
        Swal.fire({
          icon: 'error',
          title: e.error.mensaje + " " + e.error.error,
          text: 'Error al crear anuncio. El título es obligatorio.'
          });
        return throwError(e);
      })
    );
  }

  // Obtener Anuncio
  getAnunio(id): Observable<Anuncio> {
    return this.http.get<Anuncio>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e => {
        this.router.navigate(['/anuncios']); // Redirección
        console.log('Ocurrió un error. ' + e.error.mensaje);
        Swal.fire({
          icon: 'error',
          title: 'Error al editar.',
          text: e.error.mensaje
          });
        return throwError(e);
      })
    );
  }

  // Actualizar anuncio
  update(anuncio: Anuncio): Observable<any> {
    return this.http.put<any>(`${this.urlEndPoint}/${anuncio.id}`, anuncio, {headers: this.httpHeaders}).pipe(
      catchError(e => {

        if(e.status == 400) {
          return throwError(e);
        }

        console.error('Error al editar. ' + e.error.mensaje);
        Swal.fire({
          icon: 'error',
          title: 'Error al editar anuncio.',
          text: e.error.mensaje
          });
        return throwError(e);
      })
    );
  }

  // Borrar Anuncio
  delete(id: number): Observable<Anuncio> {
    return this.http.delete<Anuncio>(`${this.urlEndPoint}/${id}`, {headers: this.httpHeaders}).pipe(
      catchError(e => {
        console.error('Error al borrar. ' + e.error.mensaje);
        Swal.fire({
          icon: 'error',
          title: 'Error al eliminar anuncio.',
          text: e.error.mensaje
          });
        return throwError(e);
      })
    );
  }

  subirFoto(archivo: File, id): Observable<HttpEvent<{}>> {
    let formData = new FormData();
    formData.append("archivo", archivo);
    formData.append("id", id);

    const req = new HttpRequest('POST', `${this.urlEndPoint}/upload`, formData, {
      reportProgress: true
    });

    return this.http.request(req);

    // return this.http.post(`${this.urlEndPoint}/upload`, formData).pipe(
    //   map((response: any) => response.anuncio as Anuncio),
    //   catchError(e => {
    //     console.error('Error al subir. ' + e.error.mensaje);
    //     Swal.fire({
    //       icon: 'error',
    //       title: 'Error al subir la foto.',
    //       text: "Mensaje: " + e.error.mensaje
    //       });
    //     return throwError(e);
    //   })
    // );
  }




  // Esta es la forma usando map
  /*getAnuncios(): Observable<Anuncio[]> {
      //return of(ANUNCIOS);
      return this.http.get(this.urlEndPoint).pipe(
        map(response => response as Anuncio[])
      );
  }*/

  // Esta sería una forma, la otra es mediante map, que permite convertir el json al tipo del 'anuncio'
  // getAnuncios(): Observable<Anuncio[]> {
  //   return this.http.get<Anuncio[]>(this.urlEndPoint);
  // }

  // Devuelve un listado con los anuncios (sin paginar)
  // getAnuncios(): Observable<Anuncio[]> {
  //   return this.http.get(this.urlEndPoint).pipe(
  //     tap(
  //       response => {
  //         console.log('ClienteService: tap 1');
  //         let anuncios = response as Anuncio[];
  //         anuncios.forEach(
  //           anuncio => {
  //             console.log(anuncio.titulo);
  //           }
  //         )
  //       }
  //     ),
  //     map(response => {
  //       let anuncios = response as Anuncio[];
  //       return anuncios.map(anuncio => {
  //         anuncio.titulo = anuncio.titulo.toUpperCase();   // Para mostrar el título en la vista en MAYÚSCULAS
  //         // EL FORMATO ESPAÑOL SE HA CONFIGURADO A NIVEL GLOBAL DE LA APP EN APP.MODULE.TS
  //         // anuncio.createAt = formatDate(anuncio.createAt, 'dd-MM-yyyy', 'en-US'); // Para ver la fecha en formato dd-MM-
  //         // let datePipe = new DatePipe('en-US'); // Otra forma de cambiar el formato  de la fecha (esta línea y la siguiente)
  //         // anuncio.createAt = datePipe.transform(anuncio.createAt, 'dd/MM/yyyy');
  //         let datePipe = new DatePipe('es');
  //         // anuncio.createAt = datePipe.transform(anuncio.createAt, 'fullDate'); // También se puede formatear la fecha en la plantilla(se procede a ello)
  //         return anuncio;
  //       })
  //     }),
  //     tap(
  //       response => {
  //         console.log('ClienteService: tap 2');
  //         response.forEach(
  //           anuncio => {
  //             console.log(anuncio.titulo);
  //           }
  //         )
  //       }
  //     )
  //   );
  // }

}

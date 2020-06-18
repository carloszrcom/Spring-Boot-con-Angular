import { Injectable } from '@angular/core';
// import { CLIENTES } from './clientes.json';
import { Cliente } from './cliente';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
// import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {
  private urlEndPoint:string = 'http://localhost:8080/api/clientes';

  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(private http: HttpClient) { }

  getClientes(): Observable<Cliente[]> {
      return this.http.get<Cliente[]>(this.urlEndPoint);
  }

  create(cliente: Cliente): Observable(Cliente) {
      return this.http.post(this.urlEndPoint, cliente, )
  }

  // getClientes(): Observable<Cliente[]> {
  //   // return of(CLIENTES);
  //   // return this.http.get<Cliente[]>(this.urlEndPoint);
  //   return this.http.get(this.urlEndPoint).pipe(
  //     map((response) => response as Cliente[])
  //     // function(response) {return response as Cliente[]} Otra forma de escribirlo antes de ECMAScript 6
  //   );
  // }

}

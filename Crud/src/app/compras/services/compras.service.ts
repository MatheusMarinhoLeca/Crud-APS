import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { delay, first } from 'rxjs';
import { Page } from 'app/shared/model/page';
import { Compra } from 'app/shared/model/compra';


@Injectable({
  providedIn: 'root'
})
export class ComprasService {

  private readonly API = 'api/compras';
  page!: Page;

  constructor(private httpClient: HttpClient) { }

  cep(cep: string){
    return this.httpClient.get<any>(`${this.API}`+ `/cep/`+ cep);
  }

  list(size:number, page:number){
    return this.httpClient.get<Page>(`${this.API}`+ `?size=`+ size+ `&page=`+page )
    .pipe(
      first(),
      delay(500)
    );
  }

  save(record: Partial<Compra>){
    if(record.id){
      return this.update(record);
    }
    return this.create(record);
  }

  private create(record: Partial<Compra>){
    return  this.httpClient.post<Compra>(this.API,record);
  }

  private update(record: Partial<Compra>){
    return  this.httpClient.put<Compra>(`${this.API}`,record);
  }

  findById(id: number){
    return this.httpClient.get<Compra>(`${this.API}/${id}`);
  }



  delete(id: number){
    return this.httpClient.delete(`${this.API}/${id}`);
  }

  getCategorias(){
    return this.httpClient.get<Page>(`${this.API}`+ `/findCategoriaAsc`)
    .pipe(
      first(),
      delay(500)
    );
  }
  getFornecedores(){
    return this.httpClient.get<Page>(`${this.API}`+ `/findFornecedorDistinctByQtdDisponivel`)
    .pipe(
      first(),
      delay(500)
    );
  }
  getCompras(size:number, page:number){
    return this.httpClient.get<Page>(`${this.API}`+ `/findByQtdDisponivel`+ `?size=`+ size+ `&page=`+page)
    .pipe(
      first(),
      delay(500)
    );
  }
}

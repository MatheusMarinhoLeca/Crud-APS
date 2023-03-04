import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { delay, first } from 'rxjs';
import { Page } from 'app/shared/model/page';
import { Produto } from '../model/produto';




@Injectable({
  providedIn: 'root'
})
export class EstoqueService {

  private readonly API = 'api/produtos';
  page!: Page;

  constructor(private httpClient: HttpClient) { }

  list(size:number, page:number){
    return this.httpClient.get<Page>(`${this.API}`+ `?size=`+ size+ `&page=`+page )
    .pipe(
      first(),
      delay(500)
    );
  }

  save(record: Partial<Produto>){
    if(record.id){
      return this.update(record);
    }
    return this.create(record);
  }

  private create(record: Partial<Produto>){
    return  this.httpClient.post<Produto>(this.API,record);
  }

  private update(record: Partial<Produto>){
    return  this.httpClient.put<Produto>(`${this.API}`,record);
  }

  findById(id: number){
    return this.httpClient.get<Produto>(`${this.API}/${id}`);
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
  getProdutos(size:number, page:number){
    return this.httpClient.get<Page>(`${this.API}`+ `/findByQtdDisponivel`+ `?size=`+ size+ `&page=`+page)
    .pipe(
      first(),
      delay(500)
    );
  }
}

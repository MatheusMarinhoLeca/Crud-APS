

import { Injectable } from '@angular/core';
import {
  Router, Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot
} from '@angular/router';
import { Observable, of } from 'rxjs';
import { Produto } from '../model/produto';
import { EstoqueService } from '../services/estoque.service';

@Injectable({
  providedIn: 'root'
})
export class EstoqueResolver implements Resolve<Produto> {
  constructor(private service: EstoqueService){

  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Produto> {
    if(route.params && route.params['id']){
      return this.service.findById(route.params['id']);
    }
    return of({id: 0, nome: '', valorCompra: '', valorVenda: '',qtdDisponivel:'', categoria: '',fornecedor:''});
  }
}

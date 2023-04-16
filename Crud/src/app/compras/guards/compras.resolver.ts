

import { Injectable } from '@angular/core';
import {
  Router, Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot
} from '@angular/router';
import { Observable, of } from 'rxjs';

import { ComprasService } from '../services/compras.service';
import { Produto } from 'app/shared/model/produto';

@Injectable({
  providedIn: 'root'
})
export class ComprasResolver implements Resolve<Produto> {
  constructor(private service: ComprasService){

  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Produto> {
    if(route.params && route.params['id']){
      return this.service.findById(route.params['id']);
    }
    return of({id: 0, nome: '', valorCompra: '', valorVenda: '',qtdDisponivel:'', categoria: '',fornecedor:''});
  }
}

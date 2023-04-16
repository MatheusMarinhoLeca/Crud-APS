

import { Injectable } from '@angular/core';
import {
  Router, Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot
} from '@angular/router';
import { Observable, of } from 'rxjs';

import { VitrineService } from '../services/vitrine.service';
import { Produto } from 'app/shared/model/produto';

@Injectable({
  providedIn: 'root'
})
export class VitrineResolver implements Resolve<Produto> {
  constructor(private service: VitrineService){

  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Produto> {
    if(route.params && route.params['id']){
      return this.service.findById(route.params['id']);
    }
    return of({id: 0, nome: '', valorCompra: '', valorVenda: '',qtdDisponivel:'', categoria: '',fornecedor:''});
  }
}

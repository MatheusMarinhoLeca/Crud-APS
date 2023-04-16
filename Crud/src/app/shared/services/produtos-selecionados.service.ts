import { Injectable } from '@angular/core';
import { Produto } from 'app/estoque/model/produto';

@Injectable({
  providedIn: 'root'
})
export class ProdutosSelecionadosService {

  selectedProdutos: { produto: Produto, quantidade: number }[] = [];

  setSelectedProdutos(produtos: { produto: Produto, quantidade: number }[]) {
    this.selectedProdutos = produtos;
  }

  getSelectedProdutos() {
    return this.selectedProdutos;
  }
}

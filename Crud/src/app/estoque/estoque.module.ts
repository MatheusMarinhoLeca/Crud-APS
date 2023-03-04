import { AppMaterialModule } from './../shared/app-material/app-material.module';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { EstoqueRoutingModule } from './estoque-routing.module';
import { EstoqueComponent } from './containers/estoque/estoque.component';
import { SharedModule } from '../shared/shared.module';
import { ReactiveFormsModule } from '@angular/forms';
import {MatDialogModule} from '@angular/material/dialog';
import { EstoqueListComponent } from './components/estoque-list/estoque-list.component';
import { RelatorioCategoriasComponent } from './components/relatorio-categorias/relatorio-categorias.component';
import { RelatorioProdutosComponent } from './components/relatorio-produtos/relatorio-produtos.component';
import { RelatorioFornecedoresComponent } from './components/relatorio-fornecedores/relatorio-fornecedores.component';
import { EstoqueLogadoComponent } from './containers/estoqueLogado/estoqueLogado.component';
import { EstoqueLogadoListComponent } from './components/estoqueLogado-list/estoqueLogado-list.component';

@NgModule({
  declarations: [
    EstoqueComponent,
    EstoqueLogadoComponent,
    EstoqueListComponent,
    EstoqueLogadoListComponent,
    RelatorioCategoriasComponent,
    RelatorioProdutosComponent,
    RelatorioFornecedoresComponent

  ],
  imports: [
    CommonModule,
    EstoqueRoutingModule,
    AppMaterialModule,
    SharedModule,
    ReactiveFormsModule,
    MatDialogModule,
    SharedModule
  ],
  exports:[]
})
export class EstoqueModule { }

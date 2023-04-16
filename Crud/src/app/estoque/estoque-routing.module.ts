import { EstoqueResolver } from './guards/estoque.resolver';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EstoqueComponent } from './containers/estoque/estoque.component';
import { ModalComponent } from './containers/modal/modal.component';
import { RelatorioCategoriasComponent } from './components/relatorio-categorias/relatorio-categorias.component';
import { RelatorioProdutosComponent } from './components/relatorio-produtos/relatorio-produtos.component';
import { RelatorioFornecedoresComponent } from './components/relatorio-fornecedores/relatorio-fornecedores.component';
import { EstoqueLogadoComponent } from './containers/estoqueLogado/estoqueLogado.component';

const routes: Routes = [
  {path:'',component: EstoqueComponent},
  {path:'estoque/logado',component: EstoqueLogadoComponent},
  {path:'edit/:id',component: EstoqueComponent},
  {path:'estoque/logado',component: EstoqueLogadoComponent},
  {path:'logado/edit/:id',component: EstoqueLogadoComponent},
  {path:'relatorio/categorias',component: RelatorioCategoriasComponent},
  {path:'relatorio/produtos',component: RelatorioProdutosComponent},
  {path:'relatorio/fornecedores',component: RelatorioFornecedoresComponent},
  {path:'logado/relatorio/categorias',component: RelatorioCategoriasComponent},
  {path:'logado/relatorio/produtos',component: RelatorioProdutosComponent},
  {path:'logado/relatorio/fornecedores',component: RelatorioFornecedoresComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EstoqueRoutingModule { }

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CarrinhoComponent } from './containers/carrinho/carrinho.component';


const routes: Routes = [
  {path:'carrinho',component: CarrinhoComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CarrinhoRoutingModule { }

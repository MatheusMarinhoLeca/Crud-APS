
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo:'estoque'},
  { path: 'estoque',loadChildren: () => import('./estoque/estoque.module').then( m=>m.EstoqueModule)},
  { path: 'estoqueLogado',loadChildren: () => import('./estoque/estoque.module').then( m=>m.EstoqueModule)}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AppMaterialModule } from './../shared/app-material/app-material.module';
import { SharedModule } from '../shared/shared.module';
import { ReactiveFormsModule } from '@angular/forms';
import {MatDialogModule} from '@angular/material/dialog';
import { CarrinhoRoutingModule } from './carrinho-routing.module';
import { CarrinhoListComponent } from './components/carrinho-list/carrinho-list.component';
import { CarrinhoComponent } from './containers/carrinho/carrinho.component';



@NgModule({
  declarations: [
    CarrinhoListComponent,
    CarrinhoComponent
  ],
  imports: [
    CommonModule,
    CarrinhoRoutingModule,
    AppMaterialModule,
    SharedModule,
    ReactiveFormsModule,
    MatDialogModule,
  ]
})
export class CarrinhoModule { }

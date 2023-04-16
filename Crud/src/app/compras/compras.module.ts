import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AppMaterialModule } from './../shared/app-material/app-material.module';
import { SharedModule } from '../shared/shared.module';
import { ReactiveFormsModule } from '@angular/forms';
import {MatDialogModule} from '@angular/material/dialog';

import { ComprasRoutingModule } from './compras-routing.module';
import { ComprasListComponent } from './components/compras-list/compras-list.component';
import { ComprasComponent } from './containers/compras/compras.component';


@NgModule({
  declarations: [
    ComprasListComponent,
    ComprasComponent
  ],
  imports: [
    CommonModule,
    ComprasRoutingModule,
    AppMaterialModule,
    SharedModule,
    ReactiveFormsModule,
    MatDialogModule,
    SharedModule
  ]
})
export class ComprasModule { }

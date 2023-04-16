import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { VitrineRoutingModule } from './vitrine-routing.module';
import { VitrineListComponent } from './components/vitrine-list/vitrine-list.component';
import { VitrineComponent } from './containers/vitrine/vitrine.component';
import { AppMaterialModule } from 'app/shared/app-material/app-material.module';
import { SharedModule } from '../shared/shared.module';
import { ReactiveFormsModule } from '@angular/forms';
import {MatDialogModule} from '@angular/material/dialog';
import { ProdutoQuantidadeComponent } from './components/produto-quantidade/produto-quantidade.component';

@NgModule({
  declarations: [
    VitrineListComponent,
    VitrineComponent,
    ProdutoQuantidadeComponent
  ],
  imports: [
    CommonModule,
    VitrineRoutingModule,
    AppMaterialModule,
    SharedModule,
    ReactiveFormsModule,
    MatDialogModule,
    SharedModule
  ]
})
export class VitrineModule { }

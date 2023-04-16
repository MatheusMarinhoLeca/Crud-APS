import { AppMaterialModule } from './app-material/app-material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ErrorDialogComponent } from './components/error-dialog/error-dialog.component';
import { CategoriaPipe } from './pipes/categoria.pipe';
import { ModalComponent } from 'app/estoque/containers/modal/modal.component';
import { PopupComponent } from './components/popup/popup.component';




@NgModule({
  declarations: [
    ErrorDialogComponent,
    CategoriaPipe,
    ModalComponent,
    PopupComponent
  ],
  imports: [
    AppMaterialModule,
    CommonModule,
  ],
  exports:[
    ErrorDialogComponent,
    PopupComponent,
    CategoriaPipe
  ]
})
export class SharedModule { }

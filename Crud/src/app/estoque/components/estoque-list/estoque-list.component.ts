
import { Router, ActivatedRoute } from '@angular/router';
import { Component, EventEmitter, Input, Output } from '@angular/core';

import { MatDialog } from '@angular/material/dialog';
import { ModalComponent } from '../../containers/modal/modal.component';
import { Page } from 'app/shared/model/page';
import { catchError, map, Observable, of } from 'rxjs';
import { MatSnackBar, MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition } from '@angular/material/snack-bar';
import { EstoqueService } from '../../services/estoque.service';
import { Produto } from 'app/estoque/model/produto';

@Component({
  selector: 'app-estoque-list',
  templateUrl: './estoque-list.component.html',
  styleUrls: ['./estoque-list.component.scss']
})
export class EstoqueListComponent {

  @Input() estoque: Produto[]=[];
  @Output() add = new EventEmitter(false);
  @Output() edit = new EventEmitter(false);
  @Output() remove = new EventEmitter(false);
  page: Page | undefined;
  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'bottom';

  readonly displayedColumns = ['nome','valorVenda','qtdDisponivel','categoria','fornecedor', 'acoes'];

  constructor(
    public dialog: MatDialog,
    private _snackBar: MatSnackBar,
    private estoqueService : EstoqueService
  ){

  }

  onAdd(){
    this.add.emit(true);
  }

  onEdit(produto: Produto){
    this.edit.emit(produto);
  }

  onRemove(produto: Produto){
    this.remove.emit(produto);
  }

  openSnackBar(aviso: string, recado: string) {
    this._snackBar.open(aviso, recado, {
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
    });
  }
}

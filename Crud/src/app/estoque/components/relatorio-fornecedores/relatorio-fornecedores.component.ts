import { Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSnackBar, MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition } from '@angular/material/snack-bar';
import { EstoqueService } from 'app/estoque/services/estoque.service';
import { Page } from 'app/shared/model/page';
import { catchError, map, Observable, of } from 'rxjs';

@Component({
  selector: 'app-relatorio-fornecedores',
  templateUrl: './relatorio-fornecedores.component.html',
  styleUrls: ['./relatorio-fornecedores.component.scss']
})
export class RelatorioFornecedoresComponent {
  lista: Observable<String[]>;
  page!: Page;
  @ViewChild(MatPaginator)
  paginator!: MatPaginator;
  lowValue: number = 0;
  highValue: number = 10;
  length : number = 0;
  index : number = 0;
  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'bottom';
  readonly displayedColumns = ['fornecedores'];
  constructor (private estoqueService : EstoqueService,public dialog: MatDialog,
    private _snackBar: MatSnackBar

    ){
      this.lista = this.estoqueService.getFornecedores()
    .pipe(
      map(page => {
        this.page = page;
        if(page==null){
          catchError(error =>{
            this.openSnackBar('Erro ao carregar os produtos', 'Entendido');
            console.log(error);
            return of([]);
          })
        }this.length=page.totalElements;
        return page.content.slice(0,this.highValue);
      })
    );

    this.lista.subscribe(result => {if(result.length==0){
      this.openSnackBar('Não há produtos catalogados', 'Entendido');
    }});
    }
  openSnackBar(arg0: string, arg1: string) {
    throw new Error('Method not implemented.');
  }
  public getPaginatorData(event: PageEvent): PageEvent {
    this.index = event.pageIndex * event.pageSize;
    this.lowValue = event.pageIndex ;
    this.highValue =  event.pageSize;

    this.reload();
    return event;
  }
  reload(){
    this. lista = this.estoqueService.getFornecedores()
    .pipe(
      map(page => {
        this.page = page;
        if(page==null){
          catchError(error =>{
            this.openSnackBar('Erro ao carregar os produtos', 'Entendido');
            console.log(error);
            return of([]);
          })
        } this.length=page.totalElements;

        return page.content.slice(this.index,this.index+this.highValue);

      })
    );
  }

}

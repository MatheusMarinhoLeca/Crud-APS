import { Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSnackBar, MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Produto } from 'app/estoque/model/produto';
import { EstoqueService } from 'app/estoque/services/estoque.service';
import { Page } from 'app/shared/model/page';
import { catchError, map, Observable, of } from 'rxjs';

@Component({
  selector: 'app-relatorio-produtos',
  templateUrl: './relatorio-produtos.component.html',
  styleUrls: ['./relatorio-produtos.component.scss']
})
export class RelatorioProdutosComponent {
  estoque: Observable<Produto[]>;
  page: Page | undefined;
  @ViewChild(MatPaginator)
  paginator!: MatPaginator;
  lowValue: number = 0;
  highValue: number = 10;
  length : number = 0;
  index : number = 0;
  readonly displayedColumns = ['nome','valorVenda','qtdDisponivel','categoria','fornecedor'];
  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'bottom';
  constructor (private estoqueService : EstoqueService,public dialog: MatDialog,
    private _snackBar: MatSnackBar, private router: Router,
    private route: ActivatedRoute

    ){
      this.estoque  = this.estoqueService.getProdutos(this.highValue,this.lowValue)
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

    this.estoque .subscribe(result => {if(result.length==0){
      this.openSnackBar('Não há produtos catalogados', 'Entendido');
    }});
  }
  openSnackBar(aviso: string, recado: string) {
    this._snackBar.open(aviso, recado, {
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
    });
  }



  public getPaginatorData(event: PageEvent): PageEvent {
    this.index = event.pageIndex * event.pageSize;
    this.lowValue = event.pageIndex ;
    this.highValue =  event.pageSize;

    this.reload();
    return event;
  }
  reload(){
    this.estoque = this.estoqueService.getProdutos(this.highValue,this.lowValue)
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

import { Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSnackBar, MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { ComprasService } from 'app/compras/services/compras.service';
import { ModalComponent } from 'app/estoque/containers/modal/modal.component';
import { PopupComponent } from 'app/shared/components/popup/popup.component';
import { Compra } from 'app/shared/model/compra';
import { Page } from 'app/shared/model/page';
import { Produto } from 'app/shared/model/produto';
import { Observable, catchError, map, of, startWith, switchMap } from 'rxjs';

@Component({
  selector: 'app-compras',
  templateUrl: './compras.component.html',
  styleUrls: ['./compras.component.scss']
})
export class ComprasComponent {

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  lowValue: number = 0;
  highValue: number = 10;
  length : number = 0;

  compras$: Observable<Compra[]>;
  page: Page | undefined;
  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'bottom';



  constructor (
    private _snackBar: MatSnackBar,
    private router: Router,
    private route: ActivatedRoute,
    public dialog: MatDialog,
    private comprasService : ComprasService
    ){
      this.compras$ = this.comprasService.list(this.highValue, this.lowValue)
    .pipe(
      map(page => {
        this.page = page;
        if (!page) {
          catchError(error => {
            this.openSnackBar('Erro ao carregar os produtos', 'Entendido');
            console.log(error);
            return of([]);
          });
        } else {
          this.length = page.totalElements;
        }
        return page ? page.content : [];
      })
    );

      this.compras$.subscribe(result => {if(result.length==0){
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

    this.lowValue = event.pageIndex ;
    this.highValue =  event.pageSize;
    this.reload();
    return event;
  }


  reload(){
    this.compras$ = this.comprasService.list(this.highValue,this.lowValue)
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

        return page.content;
      })
    );
  }


}

import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSnackBar, MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { EstoqueService } from 'app/estoque/services/estoque.service';
import { PopupComponent } from 'app/shared/components/popup/popup.component';
import { Page } from 'app/shared/model/page';
import { Produto } from 'app/shared/model/produto';
import { Observable, catchError, map, of } from 'rxjs';

@Component({
  selector: 'app-vitrine',
  templateUrl: './vitrine.component.html',
  styleUrls: ['./vitrine.component.scss']
})
export class VitrineComponent {

  @ViewChild(MatPaginator)
  paginator!: MatPaginator;
  lowValue: number = 0;
  highValue: number = 10;
  length : number = 0;
  estoque$: Observable<Produto[]>;
  page: Page | undefined;
  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'bottom';



  constructor (private estoqueService : EstoqueService,
    private _snackBar: MatSnackBar,
    private router: Router,
    private route: ActivatedRoute,
    public dialog: MatDialog
    ){
    this.estoque$ = this.estoqueService.list(this.highValue,this.lowValue)
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

      this.estoque$.subscribe(result => {if(result.length==0){
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



  ngOnInit(): void {

  }
  categoriasList(){
    this.router.navigate(['relatorio/categorias'],{relativeTo: this.route})
  }

  carrinhoLink() {
    this.router.navigate(['/carrinho'],{relativeTo: this.route})
  }


  produtosList(){
    this.router.navigate(['relatorio/produtos'],{relativeTo: this.route})
  }

  fornecedoresList(){
    this.router.navigate(['relatorio/fornecedores'],{relativeTo: this.route})
  }
  onAdd(){



  }

  onEdit(produto:Produto){




  }


  onRemove(produto:Produto){
    const dialogRef = this.dialog.open(PopupComponent,{
      data: {
        mostrar: false,
        frase: "",
        letra: "",
        tempo: ""
      }
    });
    dialogRef.afterClosed().subscribe(result => {

      if(result.event=="sim"){
        this.estoqueService.delete(produto.id).subscribe(
          data=>
            this.openSnackBar('Produto deletado com sucesso! Atualizando Db...', ''), error =>{
              this._snackBar.open("Não conseguiu Deletar o produto", "Entendido");
            }
          );
        setTimeout(() => { this.router.navigate([''], {relativeTo:this.route});  }, 3000);
      }
    });



  }

  reload(){
    this.estoque$ = this.estoqueService.list(this.highValue,this.lowValue)
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

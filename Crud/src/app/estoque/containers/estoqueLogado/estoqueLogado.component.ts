import { ModalComponent } from '../modal/modal.component';
import { EstoqueService } from '../../services/estoque.service';
import { Component, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import {MatSnackBar,MatSnackBarHorizontalPosition,
  MatSnackBarVerticalPosition,} from '@angular/material/snack-bar';
  import {MatDialog, MatDialogRef} from '@angular/material/dialog';
import { catchError, Observable, of, first } from 'rxjs';;
import { map, tap } from 'rxjs/operators';

import { ActivatedRoute, Router } from '@angular/router';
import { Page } from 'app/shared/model/page';

import { Produto } from 'app/estoque/model/produto';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { PopupComponent } from 'app/shared/components/popup/popup.component';
@Component({
  selector: 'app-estoqueLogado',
  templateUrl: './estoqueLogado.component.html',
  styleUrls: ['./estoqueLogado.component.scss']
})
export class EstoqueLogadoComponent implements OnInit
 {
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


  produtosList(){
    this.router.navigate(['relatorio/produtos'],{relativeTo: this.route})
  }

  fornecedoresList(){
    this.router.navigate(['relatorio/fornecedores'],{relativeTo: this.route})
  }
  onAdd(){
    this.dialog.open(ModalComponent,{
      data: {
        route: this.route,
        dataValue: "Cadastro de um novo produto",
        edit: true,
        id:0,
        nome: '',
        valorCompra: '',
        valorVenda: '',
        qtdDisponivel: '',
        categoria: '',
        fornecedor: ''
      }} );


  }

  onEdit(produto:Produto){

    this.dialog.open(ModalComponent,{
      data: {
        route: this.route,
        dataValue: "Edição de um produto",
        edit: true,
        id: produto.id,
        nome: produto.nome,
        valorCompra: produto.valorCompra,
        valorVenda: produto.valorVenda,
        qtdDisponivel: produto.qtdDisponivel,
        categoria: produto.categoria,
        fornecedor: produto.fornecedor
      }});


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
            this._snackBar.open('Produto deletado com sucesso! Atualizando Db...', '', {duration: 5000}), error =>{
              this._snackBar.open("Não conseguiu Deletar o produto", "Entendido");
            }
          );
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


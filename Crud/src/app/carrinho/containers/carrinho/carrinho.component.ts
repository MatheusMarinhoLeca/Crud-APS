
import { CarrinhoService } from '../../services/carrinho.service';
import { Component, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import {MatSnackBar,MatSnackBarHorizontalPosition,
  MatSnackBarVerticalPosition,} from '@angular/material/snack-bar';
  import {MatDialog, MatDialogRef} from '@angular/material/dialog';
import { catchError, Observable, of, first, BehaviorSubject, from, forkJoin } from 'rxjs';;
import { map, mergeMap, switchMap, tap } from 'rxjs/operators';

import { ActivatedRoute, Router } from '@angular/router';
import { Page } from 'app/shared/model/page';


import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { PopupComponent } from 'app/shared/components/popup/popup.component';
import { Produto } from 'app/shared/model/produto';
import { ModalComponent } from 'app/estoque/containers/modal/modal.component';
import { ProdutosSelecionadosService } from 'app/shared/services/produtos-selecionados.service';
import { EstoqueService } from 'app/estoque/services/estoque.service';
import { ComprasService } from 'app/compras/services/compras.service';
import { Compra } from 'app/shared/model/compra';

@Component({
  selector: 'app-carrinho',
  templateUrl: './carrinho.component.html',
  styleUrls: ['./carrinho.component.scss']
})
export class CarrinhoComponent implements OnInit{
  @ViewChild(MatPaginator)
  paginator!: MatPaginator;
  lowValue: number = 0;
  highValue: number = 10;
  length : number = 0;
  selectedProdutos: { produto: Produto, quantidade: number }[] = [];
  private carrinhoSubject = new BehaviorSubject<{ produto: Produto, quantidade: number }[]>([]);
  carrinho$ = this.carrinhoSubject.asObservable();
  qrCodeImageUrl?: string;
  page: Page | undefined;
  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'bottom';



  constructor (private carrinhoService : CarrinhoService,
    private _snackBar: MatSnackBar,
    private router: Router,
    private route: ActivatedRoute,
    public dialog: MatDialog,
    private estoqueService : EstoqueService,
    private sharedService : ProdutosSelecionadosService,
    private comprasService : ComprasService,
    ){
    this.carrinho$ = this.carrinhoService.list(this.highValue,this.lowValue)
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

      this.carrinho$.subscribe(result => {if(result.length==0){
      this.openSnackBar('Não há produtos catalogados', 'Entendido');
    }});
  }

  ngOnInit() {
    this.selectedProdutos = this.sharedService.getSelectedProdutos();

    this.carrinho$ = of(this.selectedProdutos).pipe(
      switchMap(produtos =>
        Promise.all(
          produtos.map(p =>
            this.estoqueService.findById(p.produto.id).pipe(
              map(produto => ({
                produto,
                quantidade: Math.min(p.quantidade, parseInt(produto.qtdDisponivel)),
              }))
            )
          )
        )
      ),
      mergeMap(produtos => forkJoin(produtos)),
      map(produtos =>
        produtos.filter(p => p.produto !== null && p.quantidade > 0).map(p => ({
          produto: p.produto!,
          quantidade: p.quantidade,
        }))
      )
    );




    console.log(this.selectedProdutos);
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

  realizarCompra(){
    const dialogRef = this.dialog.open(PopupComponent,{
      data: {
        mostrar: true,
      }
    });

    dialogRef.componentInstance.cepSelected.subscribe((cep: string) => {
      const cepData = this.handleCepSelected(cep);
      console.log(cepData);

      dialogRef.close({event:'enviar'});

      const selectedProdutos = this.sharedService.getSelectedProdutos();
      const produtosIds = selectedProdutos.map(p => p.produto.id);
      const quantidade = selectedProdutos.reduce((total, p) => total + p.quantidade, 0).toString();
      const valorTotal = selectedProdutos.reduce((total, p) => total + (p.quantidade * parseFloat(p.produto.valorVenda)), 0).toFixed(2);

      const compra: Compra = {
        id: 0,
        quantidade: quantidade,
        valorTotal: valorTotal,
        produtos: produtosIds
      };

      this.comprasService.cep(cepData).subscribe(
        data => {
          if (data.cep != null) {
            this.comprasService.save(compra).subscribe(
              data => {
                this._snackBar.open('Pagamento enviado com sucesso! Atualizando Db...', '', {duration: 5000});
              },
              error => {
                this._snackBar.open("Não conseguiu salvar a compra", "Entendido");
              }
            );
          } else {
            this._snackBar.open("Cep inválido! Seu pagamento não foi enviado.", "Entendido");
          }
        },
        error => {
          this._snackBar.open("Não conseguiu enviar o pagamento", "Entendido");
        }
      );

    });
  }

  handleCepSelected(cep: string) {
    return cep;
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
        this.carrinhoService.delete(produto.id).subscribe(
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
    this.carrinho$ = this.carrinhoService.list(this.highValue,this.lowValue)
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

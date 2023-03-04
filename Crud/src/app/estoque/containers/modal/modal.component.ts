
import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, NonNullableFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { EstoqueService } from '../../services/estoque.service';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import { Produto } from 'app/estoque/model/produto';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.scss']
})
export class ModalComponent implements OnInit {
  valor : String = "";
  form = this.formBuilder.group({
    id: 0,
    nome : ['',[Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
    valorCompra : ['', [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
    valorVenda : ['',[Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
    qtdDisponivel : ['',[Validators.required]],
    categoria : ['',[Validators.required]],
    fornecedor : ['',[Validators.required, Validators.minLength(2), Validators.maxLength(100)]]
  });
  static valor: string;
  produto: Produto | undefined;


  constructor(public dialog: MatDialog, private router: Router,
    private route: ActivatedRoute, private formBuilder: NonNullableFormBuilder
    ,private service: EstoqueService,private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: any) {

    }

  ngOnInit(): void {
    const edit= JSON.stringify(this.data.edit);
    const id= this.data.id
    this.valor = JSON.stringify(this.data.dataValue).slice(1, -1);

      this.form.setValue({
      id:this.data.id,
      nome:this.data.nome,
      valorCompra:this.data.valorCompra,
      valorVenda:this.data.valorVenda,
      qtdDisponivel:this.data.qtdDisponivel,
      categoria:this.data.categoria,
      fornecedor:this.data.fornecedor
    });

  }


  openDialog() {

    this.dialog.open(DialogElementsExampleDialog);
  }

  onSubmit(){
    const edit= JSON.stringify(this.data.edit);
    const id= this.data.id

    if(edit=="true" && this.form.value.id!=0){
      this.service.save(this.form.value).subscribe(data=>this._snackBar.open("Produto atualizado com sucesso, atualizando DB...", ""), error =>{
        this._snackBar.open("Não conseguiu atualizar o produto", "Entendido");
      });


    }else{
      this.service.save(this.form.value).subscribe(data=>this._snackBar.open("Produto cadastrado com sucesso, atualizando DB...", "", {duration: 5000}), error =>{
      this._snackBar.open("Não conseguiu cadastrar o produto", "Entendido");
    });
    }
    setTimeout(() => { this.router.navigate([''], {relativeTo:this.route}); }, 3000);


  }

  onCancel(){
    this.router.navigate([''], {relativeTo:this.route});
  }
  getErrorMessage(fieldName: string){
    const field = this.form.get(fieldName);
    if(field?.hasError('required')){
      return 'Campo obrigatório';
    }
    if(field?.hasError('minlength')){
     const requiredLength=field.errors ? field.errors['minlength']['requiredLength'] : 3;
      return 'O tamanho mínimo precisa ser de ' + requiredLength + " caracteres";
    }
    if(field?.hasError('maxlength')){
      const requiredLength=field.errors ? field.errors['maxlength']['requiredLength'] : 100;
       return 'O tamanho mínimo precisa ser de ' + requiredLength + " caracteres";
     }
    return 'Campo inválido'
  }
}
export class DialogElementsExampleDialog {}

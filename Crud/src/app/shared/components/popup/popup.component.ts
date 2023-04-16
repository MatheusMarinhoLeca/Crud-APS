import { Component, EventEmitter, Inject, OnInit, Output } from '@angular/core';
import { FormBuilder, NonNullableFormBuilder, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { __values } from 'tslib';

@Component({
  selector: 'app-popup',
  templateUrl: './popup.component.html',
  styleUrls: ['./popup.component.scss']
})


export class PopupComponent  {
  @Output() cepSelected = new EventEmitter<string>();
  form = this.formBuilder.group({
    cep : ['',[Validators.required, Validators.minLength(2), Validators.maxLength(8)]],
  });
  mostrar : boolean = false;
  constructor (
    public dialogRef: MatDialogRef<PopupComponent>,
    private router: Router,
    private route: ActivatedRoute,
    public dialog: MatDialog,
    private formBuilder: NonNullableFormBuilder,
    @Inject(MAT_DIALOG_DATA) public data: any
    ) {

      if(this.data.mostrar) {
        this.mostrar=this.data.mostrar;
      }
    }



  getErrorMessage(fieldName: string) {
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

  openPopup(enterAnimationDuration: string, exitAnimationDuration: string) {
    this.dialog.open(DialogAnimationsExampleDialog, {
      width: '250px',
      enterAnimationDuration,
      exitAnimationDuration,
    });
  }

  onClick() {
    this.dialogRef.close({event:'sim'});
    setTimeout(() => { this.router.navigate([''], {relativeTo:this.route}); }, 3000)
    setTimeout(() => { this.router.navigate(['estoque/logado'], {relativeTo:this.route}); }, 3000)
  }

  enviar() {
    this.dialogRef.close({event:'enviar'});
    this.cepSelected.emit(this.form.value.cep);
  }
}


export class DialogAnimationsExampleDialog {
  constructor(public dialogRef: MatDialogRef<DialogAnimationsExampleDialog>) {}
}

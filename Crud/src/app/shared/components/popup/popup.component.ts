import { Component, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { __values } from 'tslib';

@Component({
  selector: 'app-popup',
  templateUrl: './popup.component.html',
  styleUrls: ['./popup.component.scss']
})
export class PopupComponent {
  mostrar : boolean = false;
  frase : string="";
  letra : string="";
  tempo : string="";
  value: boolean = false;
  constructor ( public dialogRef: MatDialogRef<PopupComponent>,
    public dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) public data: any
    ){
      console.log(this.data)
      if(this.data.mostrar){
        this.mostrar=this.data.mostrar;
        this.frase=this.data.frase;
        if(this.data.letra==""){
          this.letra="Não foi encontrada uma vogal";
        }else{
          this.letra=this.data.letra;
        }

        this.tempo=this.data.tempo;
    }else{
      this.frase=this.data.frase;
      if(this.data.vogal==""){
        this.letra="Não foi encontrada uma vogal";
      }else{
        this.letra=this.data.letra;
      }
      this.tempo=this.data.tempo;
    }




}
openPopup(enterAnimationDuration: string, exitAnimationDuration: string) {
  this.dialog.open(DialogAnimationsExampleDialog, {
    width: '250px',
    enterAnimationDuration,
    exitAnimationDuration,
  });


}
onClick(){
  this.dialogRef.close({event:'sim'});
}
}


export class DialogAnimationsExampleDialog {
  constructor(public dialogRef: MatDialogRef<DialogAnimationsExampleDialog>) {}
}

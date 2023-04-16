import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-produto-quantidade',
  templateUrl: './produto-quantidade.component.html',
  styleUrls: ['./produto-quantidade.component.scss']
})
export class ProdutoQuantidadeComponent {
  @Input() produto: any;
  @Output() quantidadeChange = new EventEmitter<number>();
  quantidade = 0;

  ngOnInit() {
    this.quantidadeChange.emit(this.quantidade);
  }

  increment() {
    this.quantidade++;
    this.quantidadeChange.emit(this.quantidade);
  }

  decrement() {
    if (this.quantidade > 0) {
      this.quantidade--;
      this.quantidadeChange.emit(this.quantidade);
    }
  }

}

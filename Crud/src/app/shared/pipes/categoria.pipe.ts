import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'categoria'
})
export class CategoriaPipe implements PipeTransform {

  transform(value: string): string {
    switch(value){
      case 'Alimento':
        return 'kitchen';
      case 'Eletrônico':
        return 'smartphone';
      case 'Eletrodoméstico':
        return 'local_laundry_service';
      case 'Móvel':
        return 'bed';
      case 'Material de escritório':
        return 'light';
      default:
        return 'category';
    }

  }

}

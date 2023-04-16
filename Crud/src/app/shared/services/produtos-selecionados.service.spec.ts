import { TestBed } from '@angular/core/testing';

import { ProdutosSelecionadosService } from './produtos-selecionados.service';

describe('ProdutosSelecionadosService', () => {
  let service: ProdutosSelecionadosService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProdutosSelecionadosService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

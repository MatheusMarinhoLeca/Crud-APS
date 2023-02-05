import { TestBed } from '@angular/core/testing';

import { EstoqueResolver } from './estoque.resolver';

describe('EstoqueResolver', () => {
  let resolver: EstoqueResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(EstoqueResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});

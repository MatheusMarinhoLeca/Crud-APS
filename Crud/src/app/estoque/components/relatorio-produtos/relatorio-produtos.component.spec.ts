import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RelatorioProdutosComponent } from './relatorio-produtos.component';

describe('RelatorioProdutosComponent', () => {
  let component: RelatorioProdutosComponent;
  let fixture: ComponentFixture<RelatorioProdutosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RelatorioProdutosComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RelatorioProdutosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

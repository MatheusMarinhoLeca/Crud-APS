import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RelatorioFornecedoresComponent } from './relatorio-fornecedores.component';

describe('RelatorioFornecedoresComponent', () => {
  let component: RelatorioFornecedoresComponent;
  let fixture: ComponentFixture<RelatorioFornecedoresComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RelatorioFornecedoresComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RelatorioFornecedoresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

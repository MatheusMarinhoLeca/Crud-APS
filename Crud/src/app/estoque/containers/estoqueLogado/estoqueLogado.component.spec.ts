import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EstoqueLogadoComponent } from './estoqueLogado.component';

describe('EstoqueComponent', () => {
  let component: EstoqueLogadoComponent;
  let fixture: ComponentFixture<EstoqueLogadoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EstoqueLogadoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EstoqueLogadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

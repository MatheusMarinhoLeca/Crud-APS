import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EstoqueLogadoListComponent } from './estoqueLogado-list.component';

describe('EstoqueLogadoListComponent', () => {
  let component: EstoqueLogadoListComponent;
  let fixture: ComponentFixture<EstoqueLogadoListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EstoqueLogadoListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EstoqueLogadoListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

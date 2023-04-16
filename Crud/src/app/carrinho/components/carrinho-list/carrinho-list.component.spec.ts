import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarrinhoListComponent } from './carrinho-list.component';

describe('CarrinhoListComponent', () => {
  let component: CarrinhoListComponent;
  let fixture: ComponentFixture<CarrinhoListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CarrinhoListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CarrinhoListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

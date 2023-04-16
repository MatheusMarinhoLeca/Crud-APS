import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProdutoQuantidadeComponent } from './produto-quantidade.component';

describe('ProdutoQuantidadeComponent', () => {
  let component: ProdutoQuantidadeComponent;
  let fixture: ComponentFixture<ProdutoQuantidadeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProdutoQuantidadeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProdutoQuantidadeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

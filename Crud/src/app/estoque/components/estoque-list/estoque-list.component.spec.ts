import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EstoqueListComponent } from './estoque-list.component';

describe('EstoqueListComponent', () => {
  let component: EstoqueListComponent;
  let fixture: ComponentFixture<EstoqueListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EstoqueListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EstoqueListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

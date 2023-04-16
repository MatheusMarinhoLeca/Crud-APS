import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VitrineListComponent } from './vitrine-list.component';

describe('VitrineListComponent', () => {
  let component: VitrineListComponent;
  let fixture: ComponentFixture<VitrineListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VitrineListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VitrineListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

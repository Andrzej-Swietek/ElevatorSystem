import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ElevatorModelComponent } from './elevator-model.component';

describe('ElevatorModelComponent', () => {
  let component: ElevatorModelComponent;
  let fixture: ComponentFixture<ElevatorModelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ElevatorModelComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ElevatorModelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

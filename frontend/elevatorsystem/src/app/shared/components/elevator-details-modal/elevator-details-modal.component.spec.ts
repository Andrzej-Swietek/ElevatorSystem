import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ElevatorDetailsModalComponent } from './elevator-details-modal.component';

describe('ElevatorDetailsModalComponent', () => {
  let component: ElevatorDetailsModalComponent;
  let fixture: ComponentFixture<ElevatorDetailsModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ElevatorDetailsModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ElevatorDetailsModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

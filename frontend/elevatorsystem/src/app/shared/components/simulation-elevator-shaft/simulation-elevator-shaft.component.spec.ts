import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SimulationElevatorShaftComponent } from './simulation-elevator-shaft.component';

describe('SimulationElevatorShaftComponent', () => {
  let component: SimulationElevatorShaftComponent;
  let fixture: ComponentFixture<SimulationElevatorShaftComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SimulationElevatorShaftComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SimulationElevatorShaftComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

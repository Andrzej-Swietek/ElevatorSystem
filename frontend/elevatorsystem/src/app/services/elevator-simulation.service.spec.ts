import { TestBed } from '@angular/core/testing';

import { ElevatorSimulationService } from './elevator-simulation.service';

describe('ElevatorSimulationService', () => {
  let service: ElevatorSimulationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ElevatorSimulationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

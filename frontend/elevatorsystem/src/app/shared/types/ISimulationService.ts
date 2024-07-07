import {IElevatorData} from "./IElevatorData";
import {Observable} from "rxjs";
import {IElevatorStatus} from "./IElevatorStatus";


import UpdateRequest = SimulationService.UpdateRequest;
import PickupRequest = SimulationService.PickupRequest;
import StartRequest = SimulationService.StartRequest;

export interface ISimulationService {
  start(request: StartRequest): Observable<IElevatorData[]>;
  pickup(request: PickupRequest): Observable<IElevatorData[]>;
  update(request: UpdateRequest): Observable<void>;
  step(): Observable<IElevatorData[]>;
  status(): Observable<IElevatorStatus[]>;
}

export namespace SimulationService {
  export interface StartRequest {
    numberOfElevators: number;
    numberOfFloors: number;
  }

  export interface PickupRequest {
    floor: number;
    direction: number;
  }

  export interface UpdateRequest {
    elevatorID: number;
    newCurrentFloor: number;
    newTargetFloor: number;
  }

}

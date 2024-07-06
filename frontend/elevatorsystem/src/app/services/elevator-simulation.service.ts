import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

// Types
import {IElevatorData, IElevatorStatus, ISimulationService} from "@shared/types";
import {SimulationService} from "@shared/types";
import StartRequest = SimulationService.StartRequest;
import PickupRequest = SimulationService.PickupRequest;
import UpdateRequest = SimulationService.UpdateRequest;

@Injectable({
  providedIn: 'root'
})
export class ElevatorSimulationService implements ISimulationService{
  private URL : string = "http://localhost:8080/elevator-system";
  private http: HttpClient = inject(HttpClient);

  public constructor() {
  }


  public start(request: StartRequest): Observable<IElevatorData[]> {
    return this.http.post<IElevatorData[]>(`${this.URL}/start`, request);
  }

  public pickup(request: PickupRequest): Observable<IElevatorData[]> {
    return this.http.post<IElevatorData[]>(`${this.URL}/pickup`, request);
  }

  public update(request: UpdateRequest): Observable<void> {
    return this.http.post<void>(`${this.URL}/update`, request);
  }

  public step(): Observable<IElevatorData[]> {
    return this.http.post<IElevatorData[]>(`${this.URL}/step`, {});
  }

  public status(): Observable<IElevatorStatus[]> {
    return this.http.get<IElevatorStatus[]>(`${this.URL}/status`);
  }

  public static calculateWaitingPeoplePerFloor(elevators: IElevatorData[]): Map<number, number> {
    const waitingMap = new Map<number, number>();

    elevators.forEach((elevator: IElevatorData) => {
      // Dodawanie osób oczekujących z pickupFloorsUp
      for (const floor in elevator.pickupFloorsUp) {
        if (Object.prototype.hasOwnProperty.call(elevator.pickupFloorsUp, floor)) {
          const count = elevator.pickupFloorsUp[floor];
          const floorNumber = parseInt(floor);
          if (Number.isNaN(floorNumber)) {
            continue; // Skip if floor is not a valid number
          }
          if (waitingMap.has(floorNumber)) {
            waitingMap.set(floorNumber, (waitingMap.get(floorNumber) ?? 0) + count);
          } else {
            waitingMap.set(floorNumber, count);
          }
        }
      }

      // Dodawanie osób oczekujących z pickupFloorsDown
      for (const floor in elevator.pickupFloorsDown) {
        if (Object.prototype.hasOwnProperty.call(elevator.pickupFloorsDown, floor)) {
          const count = elevator.pickupFloorsDown[floor];
          const floorNumber = parseInt(floor);
          if (Number.isNaN(floorNumber)) {
            continue; // Skip if floor is not a valid number
          }
          if (waitingMap.has(floorNumber)) {
            waitingMap.set(floorNumber, (waitingMap.get(floorNumber) ?? 0) + count);
          } else {
            waitingMap.set(floorNumber, count);
          }
        }
      }
    });

    return waitingMap;
  }

}

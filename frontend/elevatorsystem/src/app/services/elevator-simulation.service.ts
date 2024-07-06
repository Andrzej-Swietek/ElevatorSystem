import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

// Types
import {IElevatorData, IElevatorStatus, ISimulationService} from "../shared/types";
import {SimulationService} from "../shared/types";
import StartRequest = SimulationService.StartRequest;
import PickupRequest = SimulationService.PickupRequest;
import UpdateRequest = SimulationService.UpdateRequest;

@Injectable({
  providedIn: 'root'
})
export class ElevatorSimulationService implements ISimulationService{
  private URL : string = "http://localhost:8080";
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
}

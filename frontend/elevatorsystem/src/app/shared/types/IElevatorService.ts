import {Observable} from "rxjs";
import {IElevatorData} from "./IElevatorData";
import {IElevatorStatus} from "./IElevatorStatus";

export interface IElevatorService {
  getAllElevators(): Observable<IElevatorData[]>;
  getAllElevatorsStatuses(): Observable<IElevatorStatus[]>;
  getElevatorById(id: number): Observable<IElevatorData>;
}

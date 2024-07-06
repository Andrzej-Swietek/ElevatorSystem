import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {IElevatorData, IElevatorStatus} from "@shared/types";
import {IElevatorService} from "@shared/types/IElevatorService";

@Injectable({
  providedIn: 'root'
})
export class ElevatorService implements IElevatorService{

  private URL: string = 'http://localhost:8080/elevator';
  private http: HttpClient = inject(HttpClient);
  public constructor() { }

  public getAllElevators(): Observable<IElevatorData[]> {
    return this.http.get(`${this.URL}/all`);
  }


  public getAllElevatorsStatuses(): Observable<IElevatorStatus[]> {
    return this.http.get<IElevatorStatus[]>(`${this.URL}/all-statuses`);
  }


  public getElevatorById(id: number): Observable<IElevatorData> {
    return this.http.get(`${this.URL}/${id}`);
  }
}

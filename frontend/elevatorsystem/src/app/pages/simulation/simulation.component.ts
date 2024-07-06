import {Component, inject, signal, WritableSignal, ChangeDetectorRef, NgZone} from '@angular/core';
import {JsonPipe, NgForOf, NgOptimizedImage} from "@angular/common";

import {ElevatorSimulationService} from "@services/elevator-simulation.service";
import {StartingFormComponent} from "@components/starting-form/starting-form.component";
import {IElevatorData} from "@shared/types";
import {
  SimulationElevatorShaftComponent
} from "@components/simulation-elevator-shaft/simulation-elevator-shaft.component";


@Component({
  selector: 'app-simulation',
  standalone: true,
  imports: [
    StartingFormComponent,
    JsonPipe,
    NgForOf,
    NgOptimizedImage,
    SimulationElevatorShaftComponent
  ],
  templateUrl: './simulation.component.html',
  styleUrl: './simulation.component.scss'
})
export class SimulationComponent {

  private elevatorSimulationService: ElevatorSimulationService = inject(ElevatorSimulationService);
  private cdr: ChangeDetectorRef = inject(ChangeDetectorRef);
  private ngZone: NgZone = inject(NgZone);

  protected is3D: boolean = false;
  protected isStarted: boolean = false;
  protected autoStep: boolean = false;

  public noFloors: number = 0;
  public noElevators: number = 0;

  protected simulationData: WritableSignal<IElevatorData[]> = signal<IElevatorData[]>([]);
  protected waitingPeople!: Map<number, number>;

  public toggleAutoStep(): void {
    this.autoStep = !this.autoStep;
    this.cdr.detectChanges();
  }

  public toggle3D(): void {
    this.is3D = !this.is3D;
    this.cdr.detectChanges();
  }

  public getGridStyle(): string {
    return `display: grid; grid-template-columns: 200px repeat(${this.noElevators}, 1fr);`;
  }

  public onFormSubmitted($event: { floors: number; elevators: number }) {
    this.elevatorSimulationService.start({
      numberOfElevators: $event.elevators,
      numberOfFloors: $event.floors
    }).subscribe((data) => {
      this.noFloors = $event.floors;
      this.noElevators = $event.elevators;
      if (data) {
        this.isStarted = true;
        this.simulationData.set([...data]);
        this.waitingPeople = ElevatorSimulationService.calculateWaitingPeoplePerFloor(data);
        this.ngZone.runOutsideAngular(() => {
          setTimeout(() => {
            this.cdr.detectChanges();
          });
        });
      }
    });
  }

  protected readonly Array = Array;
  protected readonly Math = Math;

  public getFloor(index: number): number { return this.noFloors - Math.floor(index/(this.noElevators+1)) - 1; }

  public helperIfLiftIsOnTheFloor(block: number){
    const floor = this.getFloor(block);
    const data = this.simulationData();
    return data[floor]?.currentFloor == this.getFloor(block) || false;
  }

  public async pickup(floor: number, direction: number): Promise<void> {
    this.elevatorSimulationService.pickup({ floor, direction })
      .subscribe((data) => {
        this.simulationData.set([...data]);
        this.waitingPeople = ElevatorSimulationService.calculateWaitingPeoplePerFloor(data);
        this.ngZone.runOutsideAngular(() => {
          setTimeout(() => {
            this.cdr.detectChanges();
          });
        });
      });
  }

  public getWaitingCountPerFloor(floor: number) {
    return (this.waitingPeople && this.waitingPeople.has(floor)) ? this.waitingPeople.get(floor)! : 0;
  }

  public makeSimulationStep(): void {
    this.elevatorSimulationService.step().subscribe((data)=>{
      this.simulationData.set([...data]);
      this.waitingPeople = ElevatorSimulationService.calculateWaitingPeoplePerFloor(data);
      this.ngZone.runOutsideAngular(() => {
        setTimeout(() => {
          this.cdr.detectChanges();
        });
      });
    });
  }

  public trackByFn(index: number, item: any): number {
    return index;
  }

  public resetSimulation(): void {
    location.reload();
  }

  public getReverseFloorIndex(index: number) {
    return this.noFloors - index - 1;
  }
}

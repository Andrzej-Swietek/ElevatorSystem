import {Component, inject, signal, WritableSignal} from '@angular/core';
import {ElevatorSimulationService} from "@services/elevator-simulation.service";
import {StartingFormComponent} from "@components/starting-form/starting-form.component";
import {IElevatorData} from "@shared/types";
import {JsonPipe, NgForOf, NgOptimizedImage} from "@angular/common";

@Component({
  selector: 'app-simulation',
  standalone: true,
  imports: [
    StartingFormComponent,
    JsonPipe,
    NgForOf,
    NgOptimizedImage
  ],
  templateUrl: './simulation.component.html',
  styleUrl: './simulation.component.scss'
})
export class SimulationComponent {

  private elevatorSimulationService: ElevatorSimulationService = inject(ElevatorSimulationService);

  protected is3D: boolean = false;
  protected isStarted: boolean = false;
  protected autoStep: boolean = false;

  public noFloors: number = 0;
  public noElevators: number = 0;

  protected simulationData:  WritableSignal<IElevatorData[]> = signal<IElevatorData[]>([]);

  public toggleAutoStep(): void {
    this.autoStep = !this.autoStep;
  }

  public toggle3D(): void {
    this.is3D = !this.is3D;
  }

  public getGridStyle(): string {
    return `display: grid; grid-template-columns: 200px repeat(${this.noElevators}, 1fr);`;
  }

  public onFormSubmitted($event: { floors: number; elevators: number }) {
    this.elevatorSimulationService.start({
      numberOfElevators: $event.elevators,
      numberOfFloors: $event.floors
    }).subscribe( (data)=> {
      this.noFloors = $event.floors;
      this.noElevators = $event.elevators;
      console.log(data)
      if (data) {
        this.isStarted = true;
        this.simulationData.set([...data]);
      }
    });
  }

  protected readonly Array = Array;
  protected readonly Math = Math;

  public getFloor(index: number): number { return this.noFloors - Math.floor(index/(this.noElevators+1)) - 1; }


  public helperIfLiftIsOnTheFloor(block: number){
    const floor = this.getFloor(block)
    const data = this.simulationData();
    return data[floor]?.currentFloor == this.getFloor(block) || false;
  }

  public async pickup(floor: number, direction: number): Promise<void> {
    this.elevatorSimulationService.pickup({ floor, direction });
  }
}

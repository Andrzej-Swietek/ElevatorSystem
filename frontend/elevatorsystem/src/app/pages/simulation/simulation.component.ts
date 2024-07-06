import {Component, inject} from '@angular/core';
import {ElevatorSimulationService} from "../../services/elevator-simulation.service";

@Component({
  selector: 'app-simulation',
  standalone: true,
  imports: [],
  templateUrl: './simulation.component.html',
  styleUrl: './simulation.component.scss'
})
export class SimulationComponent {

  private elevatorSimulationService: ElevatorSimulationService = inject(ElevatorSimulationService);

  protected is3D: boolean = false;
  protected isStarted: boolean = false;
  protected autoStep: boolean = false;

  public toggleAutoStep(): void {
    this.autoStep = !this.autoStep;
  }

  public toggle3D(): void {
    this.is3D = !this.is3D;
  }


}

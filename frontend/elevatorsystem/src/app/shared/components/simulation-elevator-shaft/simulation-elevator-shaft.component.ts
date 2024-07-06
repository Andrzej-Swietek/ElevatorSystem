import {Component, Input} from '@angular/core';
import {IElevatorData} from "@shared/types";

@Component({
  selector: 'app-simulation-elevator-shaft',
  standalone: true,
  imports: [],
  templateUrl: './simulation-elevator-shaft.component.html',
  styleUrl: './simulation-elevator-shaft.component.scss'
})
export class SimulationElevatorShaftComponent {
  @Input() elevator!: IElevatorData;
  @Input() numberOfFloors: number = 0;
  @Input() numberOfElevators: number = 0;
  @Input() elevatorIndex: number = -1;

  protected readonly Array = Array;

  ngOnInit(): void {
    console.log(this.elevator)
  }
}

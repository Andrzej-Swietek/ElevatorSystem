import {Component, EventEmitter, Input, Output} from '@angular/core';
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

  @Output() onSelected = new EventEmitter<number>();

  protected readonly Array = Array;

  ngOnInit(): void {
    // console.log(this.elevator)
  }

  public selectElevator(): void {
    this.onSelected.emit(this.elevatorIndex);
  }
}

import {Component, EventEmitter, Input, Output} from '@angular/core';
import {IElevatorData} from "@shared/types";

@Component({
  selector: 'app-elevator-details-modal',
  standalone: true,
  imports: [],
  templateUrl: './elevator-details-modal.component.html',
  styleUrl: './elevator-details-modal.component.scss'
})
export class ElevatorDetailsModalComponent {
  @Input() showModal: boolean = false;
  @Input() elevator: IElevatorData|null = null;

  @Output() close = new EventEmitter<boolean>();

  public toggleModal(): void {
    this.close.emit(false);
  }

  public getTargetFloors(): number[] {
    return [...Object.keys(this.elevator?.targetFloors || {}).map( (key)=> +key )];
  }

  public getPickupCallFloorsUp() {
    return [...Object.keys(this.elevator?.pickupFloorsUp || {}).map( (key)=> +key )];
  }

  public getPickupCallFloorsDown() {
    return [...Object.keys(this.elevator?.pickupFloorsDown || {}).map( (key)=> +key )];
  }

  protected readonly Object = Object;
}

import {Component, Input} from '@angular/core';
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
  @Input() elevator: IElevatorData;

  public toggleModal(): void {
    this.showModal = !this.showModal;
  }
}

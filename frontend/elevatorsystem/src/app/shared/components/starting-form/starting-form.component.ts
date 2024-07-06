import {Component, EventEmitter, Output} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {CommonModule, NgIf} from "@angular/common";

@Component({
  selector: 'app-starting-form',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgIf,CommonModule
  ],
  templateUrl: './starting-form.component.html',
  styleUrl: './starting-form.component.scss'
})
export class StartingFormComponent {
  protected elevatorForm: FormGroup;
  public floors: number = 0;
  public elevators: number = 0;

  @Output() submitted: EventEmitter<{
    floors: number,
    elevators: number
  }> = new EventEmitter<{ floors: number; elevators: number }>();

  public constructor(private fb: FormBuilder) {
    this.elevatorForm = this.fb.group({
      floors: ['', [Validators.required, Validators.min(1)]],
      elevators: ['', [Validators.required, Validators.min(1)]]
    });
  }


  public onSubmit(): void {
    if (this.elevatorForm.valid) {
      this.floors = this.elevatorForm.value.floors;
      this.elevators = this.elevatorForm.value.elevators;
      this.submitted.emit({ floors: this.floors, elevators: this.elevators });
    }
  }

  public reset(): void {
    this.elevatorForm.reset();
  }
}

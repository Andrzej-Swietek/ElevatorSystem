import {Component, Input} from '@angular/core';

type VerticalOrientation = 'top' | 'bottom';
type HorizontalOrientation = 'left' | 'right';
type Orientation = `${HorizontalOrientation}-${VerticalOrientation}`;


@Component({
  selector: 'app-sliced-section',
  standalone: true,
  imports: [],
  templateUrl: './sliced-section.component.html',
  styleUrl: './sliced-section.component.scss'
})
export class SlicedSectionComponent {
  @Input('fill') fill: string = '#f0f0f0';
  @Input() orientation: Orientation = 'right-bottom'; // where is 90 deg angle

  get transform(): string {
    switch (this.orientation) {
      case 'left-top':
        return 'rotate(180deg)';
      case 'right-top':
        return 'rotate(180deg) scaleX(-1)';
      case 'left-bottom':
        return 'rotate(0deg) scaleX(-1)';
      case 'right-bottom':
        return 'rotate(0deg)'; // No rotation
      default:
        console.warn(`Invalid orientation: ${this.orientation}`);
        return 'rotate(0)';
    }
  }
}

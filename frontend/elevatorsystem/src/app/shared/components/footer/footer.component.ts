import { Component } from '@angular/core';
import {SlicedSectionComponent} from "../sliced-section/sliced-section.component";
import {RouterLink, RouterLinkActive} from "@angular/router";

@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [
    SlicedSectionComponent,
    RouterLink,
    RouterLinkActive
  ],
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.scss'
})
export class FooterComponent {

    protected readonly year;

    public constructor() {
      this.year = new Date().getFullYear();
    }
}

import { Component } from '@angular/core';
import {HeroComponent} from "../../shared/components/hero/hero.component";
import {ElevatorModelComponent} from "../../shared/components/elevator-model/elevator-model.component";
import {FooterComponent} from "../../shared/components/footer/footer.component";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    HeroComponent,
    ElevatorModelComponent,
    FooterComponent
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

}

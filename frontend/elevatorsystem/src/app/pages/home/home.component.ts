import { Component } from '@angular/core';
import {HeroComponent} from "@components/hero/hero.component";
import {ElevatorModelComponent} from "@components/elevator-model/elevator-model.component";
import {FooterComponent} from "@components/footer/footer.component";

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

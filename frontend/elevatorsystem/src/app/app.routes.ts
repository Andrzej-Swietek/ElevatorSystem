import { Routes } from '@angular/router';
import {HomeComponent} from "./pages/home/home.component";
import {SimulationComponent} from "./pages/simulation/simulation.component";
import {PageNotFoundComponent} from "./pages/page-not-found/page-not-found.component";

export const routes: Routes = [
  { path: '',           title: "Elevator System | Home",           component: HomeComponent },
  { path: 'simulation', title: "Elevator System | Simulation",     component: SimulationComponent },
  { path: '**',         title: "Elevator System | Page Not Found", component: PageNotFoundComponent }
];

import {Component, inject} from '@angular/core';
import {Router} from "@angular/router";
import {NgOptimizedImage} from "@angular/common";

@Component({
  selector: 'app-page-not-found',
  standalone: true,
  imports: [
    NgOptimizedImage
  ],
  templateUrl: './page-not-found.component.html',
  styleUrl: './page-not-found.component.scss'
})
export class PageNotFoundComponent {
  private router: Router = inject(Router);
  public async goHome(): Promise<void> {
    await this.router.navigate(['/']);
  }
}

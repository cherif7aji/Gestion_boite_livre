import { Component } from '@angular/core';
import { BoiteListComponent } from './components/boite-list/boite-list.component';
import { RouterOutlet } from '@angular/router';
import { NavbarComponent } from './components/navbar/navbar.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [BoiteListComponent, RouterOutlet,NavbarComponent],
  templateUrl: `./app.component.html`,
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'frontend'
}

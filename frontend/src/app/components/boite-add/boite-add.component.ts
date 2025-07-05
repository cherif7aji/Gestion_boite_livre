import { Component } from '@angular/core';
import { BoiteService } from '../../services/boite.service';
import { Router } from '@angular/router';
import { Boite } from '../../models/boite/boite.module';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-add-boite',
  standalone: true, 
  imports: [FormsModule],
  templateUrl: './boite-add.component.html',
  styleUrls: ['./boite-add.component.css']
})
export class AddBoiteComponent {
  boite: Boite = {
    id: null,          // 'id' est initialisé à null car c'est un auto-généré côté backend
    quantite: 0,       // 'quantite' initialisée à 0 par défaut
    nom: '',          // 'name' vide par défaut
    description: '',   // 'description' vide par défaut
    pointGeo: ''       // 'pointGeo' vide par défaut
  };

  constructor(private boiteService: BoiteService, private router: Router) {}

  // Méthode pour créer la boîte
  addBoite(): void {
    this.boiteService.createBoite(this.boite).subscribe({
      next: (newBoite) => {
        console.log('Boîte ajoutée avec succès:', newBoite);
        this.router.navigate(['/listBoites']); 
      },
      error: (err) => {
        console.error('Erreur lors de l\'ajout de la boîte:', err);
      }
    });
  }
}

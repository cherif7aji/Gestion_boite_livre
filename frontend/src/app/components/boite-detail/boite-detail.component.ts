import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BoiteService } from '../../services/boite.service';
import { Boite } from '../../models/boite/boite.module';
import { CommonModule } from '@angular/common'; // Importer CommonModule pour utiliser ngIf


@Component({
  selector: 'app-boite-detail',
  standalone: true, 
  imports: [CommonModule],
  templateUrl: './boite-detail.component.html',
  styleUrls: ['./boite-detail.component.css']
})
export class BoiteDetailComponent implements OnInit {
  boite!: Boite; // Déclare une variable boite pour stocker les données de la boîte

  constructor(
    private boiteService: BoiteService, // Service pour récupérer les boîtes
    private route: ActivatedRoute // Pour récupérer les paramètres de l'URL
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id'); // Récupère l'id de la boîte depuis l'URL
    if (id) {
      this.boiteService.getBoiteById(Number(id)).subscribe(
        (boite) => {
          this.boite = boite; // Stocke les informations de la boîte dans la variable boite
        },
        (error) => {
          console.error('Erreur de récupération de la boîte', error); // Affiche une erreur en cas de problème
        }
      );
    }
  }
}

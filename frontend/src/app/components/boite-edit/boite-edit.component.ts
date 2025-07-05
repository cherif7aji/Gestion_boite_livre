import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { BoiteService } from '../../services/boite.service';
import { Boite } from '../../models/boite/boite.module';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-boite-edit',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './boite-edit.component.html',
  styleUrls: ['./boite-edit.component.css'],
})
export class BoiteEditComponent implements OnInit {
  boiteForm!: FormGroup;
  boiteId!: number;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    private boiteService: BoiteService
  ) {}

  ngOnInit(): void {
    this.boiteId = Number(this.route.snapshot.paramMap.get('id'));
    this.boiteService.getBoiteById(this.boiteId).subscribe(
      (boite) => {
        this.boiteForm = this.fb.group({
          id: [boite.id, Validators.required],
          quantite: [boite.quantite, Validators.required],
          nom: [boite.nom, Validators.required],
          description: [boite.description, Validators.required],
          pointGeo: [boite.pointGeo, Validators.required],
        });
      },
      (error) => {
        console.error('Erreur lors du chargement de la boîte', error);
      }
    );
  }

  onSubmit(): void {
    if (this.boiteForm.valid) {
      const updatedBoite = this.boiteForm.value as Boite;
      this.boiteService.updateBoite(updatedBoite).subscribe(
        () => {
          console.log('Boîte mise à jour avec succès');
          this.router.navigate(['/listBoites']);
        },
        (error) => {
          console.error('Erreur lors de la mise à jour de la boîte', error);
        }
      );
    }
  }

  onCancel(): void {
    this.router.navigate(['/listBoites']);
  }
}

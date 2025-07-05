import { Component, OnInit } from '@angular/core';
import { BoiteService } from '../../services/boite.service';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { FormsModule } from '@angular/forms';
import { Boite } from '../../models/boite/boite.module';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { ReservationDialogComponent } from '../reservation-dialog/reservation-dialog.component';
import { Reservation } from '../../models/user-response/user-response.module';
import { ReservationBoiteDialogComponent } from '../reservation-boite-dialog/reservation-boite-dialog.component';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';


@Component({
  selector: 'app-boite-list',
  standalone: true,
  imports: [CommonModule, MatTableModule, FormsModule, MatIconModule,MatTooltipModule,],
  templateUrl: './boite-list.component.html',
  styleUrls: ['./boite-list.component.css'],
})
export class BoiteListComponent implements OnInit {
  boites: Boite[] = []; 
  displayedColumns: string[] = ['id', 'quantite', 'name', 'description', 'pointGeo', 'actions']; // Colonnes affichées
  isReservationsVisible: boolean = false; // To toggle reservations visibility
  reservations: Reservation[] = []; // To store reservations for the selected Boîte
  users: any[] = []; // Stocke tous les utilisateurs
  currentUser: any = null; // Stocke les détails de l'utilisateur connecté


  filteredBoites: Boite[] = []; 
  searchText: string = '';
  sortDirection: 'asc' | 'desc' = 'asc'; 

  pageIndex: number = 0; // Current page index
  pageSize: number = 10; // Number of items per page
  totalPages: number = 0; // Total number of pages


  constructor(private boiteService: BoiteService, private router: Router, private dialog: MatDialog ) {}

  ngOnInit(): void {
    this.boiteService.getBoites().subscribe(
      (data) => {
        this.boites = data;
        this.applyFilters(); // Applique les filtres pour initialiser
      },
      (error) => {
        console.error('Erreur lors de la récupération des données', error);
      }
    );

     // Récupérer tous les utilisateurs
     this.boiteService.getAllUsers().subscribe(
      (data) => {
        this.users = data;
        this.setCurrentUser(); // Identifier l'utilisateur connecté
      },
      (error) => {
        console.error('Erreur lors de la récupération des utilisateurs', error);
      }
    );
  }

  
 
  onUpdate(boite: Boite): void {
    this.router.navigate([`/editBoite/${boite.id}`]);
  }

  openReservationsDialog(boiteId: number): void {
    this.boiteService.getBoiteReservations(boiteId).subscribe(
      (data) => {
        this.dialog.open(ReservationBoiteDialogComponent, {
          width: '600px',
          data: { reservations: data.reservations },
        });
      },
      (error) => {
        console.error('Erreur lors de la récupération des réservations', error);
      }
    );
  }

   // Détecter l'utilisateur connecté
   setCurrentUser(): void {
    const userId = localStorage.getItem('userId'); // ID de l'utilisateur stocké dans localStorage
    if (userId) {
      this.currentUser = this.users.find((user) => user.id === parseInt(userId, 10));
    }
    if (this.currentUser) {
      console.log('Utilisateur connecté:', this.currentUser);
    } else {
      console.warn('Utilisateur connecté introuvable.');
    }
  }

  // Vérifier si l'utilisateur est ADMIN
  isAdmin(): boolean {
    return this.currentUser?.roles.some((role: any) => role.name === 'ADMIN');
  }
  
  
  onDelete(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer cette boîte?')) {
      this.boiteService.deleteBoite(id).subscribe(
        () => {
          console.log('Boîte supprimée avec succès');
          this.boites = this.boites.filter((boite) => boite.id !== id); 
          this.applyFilters(); 
        },
        (error) => {
          console.error('Erreur lors de la suppression de la boîte', error);
        }
      );
    }
  }

  // Fetch reservations for a specific Boîte
fetchReservations(boiteId: number): void {
  this.boiteService.getBoiteReservations(boiteId).subscribe(
    (data) => {
      console.log('Backend Response:', data);
      this.reservations = data.reservations;
    },
    (error) => {
      console.error('Erreur lors de la récupération des réservations', error);
    }
  );
}

  // Toggle the visibility of reservations for a specific Boîte
toggleBoiteReservations(boiteId: number): void {
  if (this.isReservationsVisible) {
    // Hide reservations if already visible
    this.isReservationsVisible = false;
    this.reservations = [];
  } else {
    // Fetch and display reservations
    this.fetchReservations(boiteId);
    this.isReservationsVisible = true;
  }
}

  declareReservation(boite: Boite): void {
    const dialogRef = this.dialog.open(ReservationDialogComponent, {
      width: '400px',
      data: { maxQuantity: boite.quantite },
    });
  
    dialogRef.afterClosed().subscribe((result) => {
      if (result !== undefined && result > 0 && result <= (boite.quantite as unknown as number)) {
        const userId = localStorage.getItem('userId');
        const connectedUserId = userId ? parseInt(userId, 10) : null;
  
        if (!connectedUserId) {
          alert('Vous devez être connecté pour effectuer une réservation.');
          return;
        }
  
        const payload = {
          utilisateur: connectedUserId,
          boite: boite.id as unknown as number,
          reservation: result,
        };
  
        console.log('Payload:', JSON.stringify(payload)); // Log the payload for debugging
  
        this.boiteService.reserveBoite(payload).subscribe(
          () => {
            alert('Réservation réussie !');
            boite.quantite = (boite.quantite as unknown as number) - result;
          },
          (error) => {
            alert('Erreur lors de la réservation:',);
          }
        );
      } else if (result !== undefined) {
        alert('Quantité invalide.');
      }
    });
  }
  
  // Méthode pour ajouter une boîte
  onAdd(): void {
    this.router.navigate(['/add']);
  }

  // Méthode pour naviguer vers la carte
  carte(): void {
    this.router.navigate(['/map']);
  }
  rest(): void {
    this.router.navigate(['/userHome']);
  }
  
  

  // Méthode pour naviguer vers les détails d'une boîte
  onRowClick(id: number): void {
    this.router.navigate([`/boite-detail/${id}`]);
  }


  sort(column: keyof Boite): void {
    console.log('Clicked on column:', column);
    this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
  
    // Crée une nouvelle référence pour filteredBoites après le tri
    this.filteredBoites = [...this.boites].sort((a, b) => {
      const valA = a[column];
      const valB = b[column];
  
      if (typeof valA === 'number' && typeof valB === 'number') {
        return this.sortDirection === 'asc' ? valA - valB : valB - valA;
      }
  
      if (typeof valA === 'string' && typeof valB === 'string') {
        return this.sortDirection === 'asc'
          ? valA.localeCompare(valB)
          : valB.localeCompare(valA);
      }
  
      return 0;
    });
  }
  

  applyFilters(): void {
    // Filtrer les boîtes selon le texte de recherche
    const lowerCaseSearch = this.searchText.toLowerCase();
    this.filteredBoites = this.boites.filter((boite) =>
      boite.nom.toLowerCase().includes(lowerCaseSearch) ||
      boite.description.toLowerCase().includes(lowerCaseSearch) ||
      boite.pointGeo.toLowerCase().includes(lowerCaseSearch) ||
      boite.id?.toString().includes(this.searchText) || false
    );
  
    // Recalculer les pages
    this.totalPages = Math.ceil(this.filteredBoites.length / this.pageSize);
    this.changePage(0); // Réinitialiser à la première page
  }
  

  changePage(newPageIndex: number): void {
    if (newPageIndex < 0 || newPageIndex >= this.totalPages) return;
  
    this.pageIndex = newPageIndex;
    const startIndex = this.pageIndex * this.pageSize;
    const endIndex = startIndex + this.pageSize;
  
    // Paginer uniquement les résultats filtrés
    this.filteredBoites = this.boites.filter((boite) =>
      boite.nom.toLowerCase().includes(this.searchText.toLowerCase()) ||
      boite.description.toLowerCase().includes(this.searchText.toLowerCase()) ||
      boite.pointGeo.toLowerCase().includes(this.searchText.toLowerCase()) ||
      boite.id?.toString().includes(this.searchText)
    ).slice(startIndex, endIndex);
  }
  
  
}

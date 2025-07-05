import { Component, OnInit } from '@angular/core';
import { ReservationService } from '../../services/reservation.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Reservation } from '../../models/user-response/user-response.module';
import { MatTable, MatTableModule } from '@angular/material/table';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Location } from '@angular/common'; 
import { trigger, style, animate, transition } from '@angular/animations';
import { Router } from '@angular/router';
import { BoiteService } from '../../services/boite.service';

@Component({
  selector: 'app-user-home',
  standalone:true,
  imports:[MatTable,CommonModule, MatTableModule, FormsModule],
  templateUrl: './user-home.component.html',
  styleUrls: ['./user-home.component.css'],
  animations: [
    trigger('fadeInOut', [
      transition(':enter', [style({ opacity: 0 }), animate('300ms ease-in', style({ opacity: 1 }))]),
      transition(':leave', [animate('300ms ease-out', style({ opacity: 0 }))]),
    ]),
  ],
})
export class UserHomeComponent implements OnInit {
  reservations: Reservation[] = [];
  displayedColumns: string[] = ['boiteName', 'reservationCount', 'actions']; // Define displayed columns
  isTableVisible: boolean = false;
  users: any[] = []; // Stocke tous les utilisateurs
  currentUser: any = null; // Stocke les détails de l'utilisateur connecté


  constructor(
    private reservationService: ReservationService,
    private snackBar: MatSnackBar,
    private location: Location,
    private router: Router,
    private boiteService: BoiteService
  ) {}

  ngOnInit(): void {
    this.fetchReservations();

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
  

 

  fetchReservations(): void {
    const userId = localStorage.getItem('userId');
    if (!userId) {
      this.snackBar.open('Utilisateur non connecté.', 'Fermer', { duration: 3000 });
      return;
    }

    this.reservationService.getUserReservations(Number(userId)).subscribe(
      (data) => {
        console.log('Backend Response:', data);
        this.reservations = data.reservations;
      },
      (error) => {
        console.error('Erreur lors de la récupération des réservations', error);
      }
    );
  }

  onReturn(reservation: Reservation): void {
    const utilisateurId: number = Number(reservation.utilisateur.id); // Convert to number primitive
    const boiteId: number = Number(reservation.boite.id); 
    console.log(utilisateurId);// Convert to number primitive
    console.log(boiteId);
    // Check for null or invalid values
    if (!utilisateurId || !boiteId) {
      console.error('Utilisateur ID or Boite ID is null or invalid');
      this.snackBar.open('Erreur: Utilisateur ou Boîte non valide.', 'Fermer', { duration: 3000 });
      return;
    }
  
    // Proceed if values are valid
    this.reservationService.returnReservation(utilisateurId, boiteId).subscribe(
      () => {
        this.snackBar.open('Réservation rendue avec succès.', 'Fermer', { duration: 3000 });
        this.reservations = this.reservations.filter((r) => r.id !== reservation.id);
        
      },
      (error) => {
        console.error('Erreur lors du retour de la réservation', error);
        this.snackBar.open('Erreur lors du retour de la réservation.', 'Fermer', { duration: 3000 });
      }
    );
  }
  toggleTableVisibility(): void {
    this.isTableVisible = !this.isTableVisible;
  }

  navigateToListBoites() {
    // Assuming you use Angular Router
    this.router.navigate(['/listBoites']);
  }

  navigateToListUsers() {
    // Assuming you use Angular Router
    this.router.navigate(['/listusers']);
  }

  
}

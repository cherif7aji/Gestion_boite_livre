import { CommonModule } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { MatTableModule } from '@angular/material/table';

@Component({
  selector: 'app-reservation-boite-dialog',
  standalone: true,
  imports: [MatDialogModule, FormsModule, CommonModule, MatTableModule],
  templateUrl: './reservation-boite-dialog.component.html',
  styleUrl: './reservation-boite-dialog.component.css'
})
export class ReservationBoiteDialogComponent {

  reservations = [];

  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {
    this.reservations = data.reservations;
  }
}

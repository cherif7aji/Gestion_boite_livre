import { Component, Inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, } from '@angular/material/dialog';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';


@Component({
  selector: 'app-reservation-dialog',
  standalone: true,
  imports: [FormsModule, MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,],
  templateUrl: './reservation-dialog.component.html',
  styleUrl: './reservation-dialog.component.css'
})
export class ReservationDialogComponent {
  reservationCount: number | null = null;

  constructor(
    public dialogRef: MatDialogRef<ReservationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { maxQuantity: number }
  ) {}

  onCancel(): void {
    this.dialogRef.close();
  }

  onReserve(): void {
    this.dialogRef.close(this.reservationCount);
  }
}

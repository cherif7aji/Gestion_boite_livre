import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user-service.service';
import { MatDialog } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { ReactiveFormsModule } from '@angular/forms';
import { UserDialogComponent } from '../user-dialog/user-dialog.component';
import { MatIcon } from '@angular/material/icon';
import { MatTooltip } from '@angular/material/tooltip';

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [ReactiveFormsModule,CommonModule, MatTableModule, MatIcon, MatTooltip],
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.css'
})
export class UserListComponent implements OnInit {
  users: any[] = [];
  displayedColumns: string[] = ['id', 'nom', 'prenom', 'mail', 'username','roles','actions'];

  constructor(private userService: UserService, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.fetchUsers();
  }

  

  fetchUsers(): void {
    this.userService.getAllUsers().subscribe(
      (data) => {
        this.users = data;
      },
      (error) => {
        console.error('Erreur lors de la récupération des utilisateurs', error);
      }
    );
  }

  

  deleteUser(userId: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer cet utilisateur ?')) {
      this.userService.deleteUser(userId).subscribe(() => {
        this.fetchUsers(); // Refresh the list
      });
    }
  }

  

openCreateDialog(): void {
  const dialogRef = this.dialog.open(UserDialogComponent, {
    data: { user: null },
  });

  dialogRef.afterClosed().subscribe((result) => {
    if (result) {
      this.userService.createUser(result).subscribe(() => {
        this.fetchUsers();
      });
    }
  });
}

openEditDialog(user: any): void {
  const dialogRef = this.dialog.open(UserDialogComponent, {
    data: { user },
  });

  dialogRef.afterClosed().subscribe((updatedUser) => {
    if (updatedUser) {
      this.userService.updateUser(updatedUser.id, updatedUser).subscribe(
        () => this.fetchUsers(),
        (error) => console.error('Error updating user', error)
      );
    }
  });
}


}



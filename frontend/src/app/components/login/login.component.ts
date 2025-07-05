import { Component } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router'; // Import Router
import { User } from '../../models/auth/user'; // Adjust path as needed
import { UserDialogComponent } from '../user-dialog/user-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { UserService } from '../../services/user-service.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, MatInputModule, MatButtonModule, MatCardModule, MatFormFieldModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm = this.fb.group({
    username: ['', Validators.required],
    password: ['', Validators.required],
  });

  errorMessage: string | null = null; // To store and display error messages

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router, // Inject Router
    private userService: UserService, private dialog: MatDialog
  ) {}

  onSubmit(): void {
    // Get the form values and cast them to a User type
    const user: User = new User(
      this.loginForm.value.username!, // non-null assertion operator to ensure values are not undefined
      this.loginForm.value.password!  // non-null assertion operator to ensure values are not undefined
    );

    // Call the login method when the form is submitted
    this.authService.login(user).subscribe(
      (response) => {
        const accessToken = response.accessToken;
        const userId = response.userId;
        if (accessToken) {
          // Store token and redirect
          localStorage.setItem('accessToken', accessToken);
          localStorage.setItem('userId',userId);
          console.log('Access token stored in localStorage.');

          // Redirect to the desired route, e.g., '/listBoites'
          this.router.navigate(['/userHome']);
        } else {
          // If no token is provided in the response
          console.error('No access token received in response.');
          this.errorMessage = 'An error occurred while logging in. Please try again.';
        }
      },
      (error) => {
        console.error('Login failed', error);

        // Show an error message on the UI
        if (error.status === 401) {
          this.errorMessage = 'Invalid username or password.';
        } else {
          this.errorMessage = 'An unexpected error occurred. Please try again later.';
        }
      }
    );
  }

  openCreateDialog(): void {
    const dialogRef = this.dialog.open(UserDialogComponent, {
      data: { user: null },
    });
  
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.userService.createUser(result).subscribe(() => {
          
        });
      }
    });
  }
}

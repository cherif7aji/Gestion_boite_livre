import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(): boolean {
    const token = localStorage.getItem('accessToken'); // Vérifiez le token

    if (token) {
      return true; // Autorisé
    } else {
      // Redirige vers la page de login si non authentifié
      this.router.navigate(['/login']);
      return false;
    }
  }
}

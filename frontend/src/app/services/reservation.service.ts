import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Reservation, UserResponse } from '../models/user-response/user-response.module';
import { Boite } from '../models/boite/boite.module';

@Injectable({
  providedIn: 'root',
})
export class ReservationService {
  private apiUrl = 'http://localhost:8080/api/reservations';
  
  private apiUrl2 = 'http://localhost:8080/api/utilisateurs';

  private apiUrl3 = 'http://localhost:8080/api/boites';



  constructor(private http: HttpClient) {}

  // Get reservations for the logged-in user
  getUserReservations(userId: number): Observable<UserResponse> {
    return this.http.get<UserResponse>(`${this.apiUrl2}/allreservations/${userId}`);
  }



  returnReservation(utilisateurId: number, boiteId: number): Observable<void> {
    const payload = {
      utilisateur: utilisateurId,
      boite: boiteId,
    };
    return this.http.put<void>(`${this.apiUrl}/return`, payload);
  }
  
 
}

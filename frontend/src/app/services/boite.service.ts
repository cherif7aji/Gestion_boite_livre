import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Boite } from '../models/boite/boite.module';
import { BoiteResponse } from '../models/boite-response/boite-response.module';

@Injectable({
  providedIn: 'root',
})
export class BoiteService {
  private apiUrl = 'http://localhost:8080/api/boites/';
  private apiUrl1 = 'http://localhost:8080/api/reservations/';

  constructor(private http: HttpClient) {}

  // Récupérer toutes les boîtes
  getBoites(): Observable<Boite[]> {
    return this.http.get<Boite[]>(`${this.apiUrl}all`);
  }

  // Récupérer une boîte par ID
  getBoiteById(id: number): Observable<Boite> {
    return this.http.get<Boite>(`${this.apiUrl}${id}`);
  }

  // Créer une nouvelle boîte
  createBoite(boite: Boite): Observable<Boite> {
    return this.http.post<Boite>(`${this.apiUrl}create`, boite);
  }

  // Mettre à jour une boîte
  updateBoite(boite: Boite): Observable<Boite> {
    return this.http.put<Boite>(`${this.apiUrl}update`, boite);
  }

  // Supprimer une boîte par ID
  deleteBoite(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}delete/${id}`);
  }

  getBoiteReservations(boiteId: number): Observable<BoiteResponse> {
    return this.http.get<BoiteResponse>(`${this.apiUrl}allreservations/${boiteId}`);
  }

  private apiUrl2 = 'http://localhost:8080/api/utilisateurs';

 

  getAllUsers(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl2}/all`);
  }

  
  
  reserveBoite(reservation: { utilisateur: number; boite: number; reservation: number }): Observable<any> {
    const payload = {
      utilisateur: reservation.utilisateur,
      boite: reservation.boite,
      reservation: reservation.reservation, // Ensure keys match backend expectations
    };
    return this.http.post<any>(`${this.apiUrl1}reserve`, payload);
  }
  
}

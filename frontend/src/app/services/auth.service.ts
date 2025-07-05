import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../models/auth/user'; // Ensure correct path to User model

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth/login'; // Your Spring Boot API URL

  constructor(private httpClient: HttpClient) {}

  // The login method now sends the user information as the request body
  login(user: User): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json' // Ensure the content type is set to JSON
    });

    // POST request to the API with the user object as the body
    return this.httpClient.post<any>(this.apiUrl, user, { headers });
  }
}

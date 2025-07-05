import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private apiUrl = 'http://localhost:8080/api/utilisateurs';

  constructor(private http: HttpClient) {}

  getAllUsers(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/all`);
  }

  addUser(user: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/create`, user);
  }

  

  deleteUser(userId: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/delete/${userId}`);
  }

  createUser(user: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/create`, user);
  }
  
  updateUser(userId: number, user: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/update/${userId}`, user);
  }
  
  
}

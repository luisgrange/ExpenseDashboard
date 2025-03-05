import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { ILoginRequest } from '../features/pages/login/interfaces/ILoginRequest';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private tokenKey = 'auth-token';
  private isAuthenticatedSubject = new BehaviorSubject<boolean>(this.hasToken());

  constructor(private http: HttpClient) { }

  private hasToken(): boolean {
    return !!localStorage.getItem(this.tokenKey);
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }
  getLoggedUser(): string | null {
    return localStorage.getItem("auth-userName");
  }

  setTokenAndCredentials(response: any): void{
    localStorage.setItem("auth-token", response.token)
    localStorage.setItem("auth-userName", response.name)
  }

  login(loginRequest: ILoginRequest) {
    return this.http.post<string>('http://localhost:8080/auth/login', loginRequest, {
      withCredentials: true
    });
  }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
    this.isAuthenticatedSubject.next(false);
  }

  get isAuthenticated$() {
    return this.isAuthenticatedSubject.asObservable();
  }
}

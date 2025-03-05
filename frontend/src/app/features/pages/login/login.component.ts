import { Component, OnInit } from '@angular/core';
import { ILoginRequest } from './interfaces/ILoginRequest';
import { AuthService } from 'src/app/auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit{

  loginRequest: ILoginRequest = {
    email: "",
    password: ""
  }

  errorMessage = ""

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    
  }

  onLogin(): void{
    this.authService.login(this.loginRequest).subscribe({
      next: (response: any) => {
        this.authService.setTokenAndCredentials(response)
        this.router.navigate(['/home']);
      },
      error: (err) => {
        this.errorMessage = 'Usuário ou senha inválidos. Tente novamente.';
      }
    });
  }

}

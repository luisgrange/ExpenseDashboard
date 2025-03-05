import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/auth/auth.service';
import { IExpenseByUser } from './interfaces/IExpenseByUser';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit{

  loggedUser: string | null = ""
  expenses : IExpenseByUser[] = [
    {
      id : "111111",
      name : "Conta de luz",
      type : "Conta",
      amount: 140.00
    },
    {
      id : "22222",
      name : "Conta de luz",
      type : "Conta",
      amount: 140.00
    },
    {
      id : "333333",
      name : "Conta de luz",
      type : "Conta",
      amount: 140.00
    },
    {
      id : "44444",
      name : "Conta de luz",
      type : "Conta",
      amount: 140.00
    }
  ]

  constructor(private authService : AuthService){
    this.loggedUser = this.authService.getLoggedUser()

    console.log(this.loggedUser)
  }

  ngOnInit(): void {
   
  }

  

}

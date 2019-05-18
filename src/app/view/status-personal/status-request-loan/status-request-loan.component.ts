import { Component, OnInit } from '@angular/core';
import { ViewService } from 'src/app/services/view.service';

@Component({
  selector: 'app-status-request-loan',
  templateUrl: './status-request-loan.component.html',
  styleUrls: ['./status-request-loan.component.css']
})
export class StatusRequestLoanComponent implements OnInit {
data
  constructor(private service: ViewService) { }

  ngOnInit() {
    this.data = this.service.REQUEST
  }

}

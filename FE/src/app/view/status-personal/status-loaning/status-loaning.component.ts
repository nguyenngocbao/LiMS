import { Component, OnInit } from '@angular/core';
import { ViewService } from 'src/app/services/view.service';

@Component({
  selector: 'app-status-loaning',
  templateUrl: './status-loaning.component.html',
  styleUrls: ['./status-loaning.component.css']
})
export class StatusLoaningComponent implements OnInit {
  data
  constructor(private service: ViewService) { }

  ngOnInit() {
    this.data = this.service.LOANING
  }

}

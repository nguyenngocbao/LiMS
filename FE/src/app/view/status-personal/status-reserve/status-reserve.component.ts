import { Component, OnInit } from '@angular/core';
import { ViewService } from 'src/app/shared/services/view.service';

@Component({
  selector: 'app-status-reserve',
  templateUrl: './status-reserve.component.html',
  styleUrls: ['./status-reserve.component.css']
})
export class StatusReserveComponent implements OnInit {
  data

  constructor(private service: ViewService) { }

  ngOnInit() {
    this.data = this.service.RESERVE
    console.log(this.data)
  }

}

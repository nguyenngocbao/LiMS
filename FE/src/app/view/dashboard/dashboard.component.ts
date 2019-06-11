import { ToastrService } from 'ngx-toastr';
import { Component, OnInit } from '@angular/core';
import { ViewService } from 'src/app/services/view.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  books

  constructor(private service: ViewService) { }

  ngOnInit() {
    this.books = this.service.getBook()
  }

}

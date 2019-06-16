import { ToastrService } from 'ngx-toastr';
import { Component, OnInit } from '@angular/core';
import { ViewService } from 'src/app/services/view.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  books = []
  length = 0
  pageSize = 10
  pageSizeOptions : number[] = [5, 10, 25, 100]
  pageIndex = 0
  constructor(private service: ViewService) { }

  ngOnInit() {
    this.books = this.service.getBook()
  }

  changePage(_) {
    this.books = this.service.getBook()
  }

}

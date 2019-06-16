import { Component, OnInit } from '@angular/core';
import { BookService } from 'src/app/management/book-management/shared/services/book.services';

@Component({
  selector: 'app-status-returned',
  templateUrl: './status-returned.component.html',
  styleUrls: ['./status-returned.component.css']
})
export class StatusReturnedComponent implements OnInit {

  displayedColumns: string[] = ['no', 'name', 'quantity', 'type','author','action'];
  dataSource ;

  constructor(public service: BookService) { }

  ngOnInit() {
    //this.dataSource = this.service.getBook();
  }

}

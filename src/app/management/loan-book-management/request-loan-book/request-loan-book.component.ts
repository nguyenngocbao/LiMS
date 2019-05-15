import { Component, OnInit } from '@angular/core';
import { BookService } from '../../book-management/shared/services/book.services';
import { MatDialog } from '@angular/material';

@Component({
  selector: 'app-request-loan-book',
  templateUrl: './request-loan-book.component.html',
  styleUrls: ['./request-loan-book.component.css']
})
export class RequestLoanBookComponent implements OnInit {

  displayedColumns: string[] = ['no', 'book', 'user', 'dateRequest','action'];
  dataSource ;

  constructor(public service: BookService,public dialog: MatDialog) { }

  ngOnInit() {
    this.dataSource = this.service.getLoanBook();
  }

}

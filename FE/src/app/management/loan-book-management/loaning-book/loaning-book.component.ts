import { Component, OnInit } from '@angular/core';
import { BookService } from '../../book-management/shared/services/book.services';
import { MatDialog } from '@angular/material';
import { LoanBookService } from '../../book-management/shared/services/loanBook.services';

@Component({
  selector: 'app-loaning-book',
  templateUrl: './loaning-book.component.html',
  styleUrls: ['./loaning-book.component.css']
})
export class LoaningBookComponent implements OnInit {
 

  displayedColumns: string[] = ['no', 'book', 'user', 'dateRequest','action'];
  dataSource ;

  constructor(public service: LoanBookService,public dialog: MatDialog) { }

  ngOnInit() {
   // this.dataSource = this.service.getLoanBook();
  }
  refesh(): any {
    
  }

}

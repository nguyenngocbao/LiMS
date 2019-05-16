import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { BookService } from '../book-management/shared/services/book.services';
import { BookDetailComponent } from '../book-management/book-detail/book-detail.component';

@Component({
  selector: 'app-loan-book-management',
  templateUrl: './loan-book-management.component.html',
  styleUrls: ['./loan-book-management.component.css']
})
export class LoanBookManagementComponent implements OnInit {

  

  constructor(public service: BookService,public dialog: MatDialog) { }

  ngOnInit() {
  }

}

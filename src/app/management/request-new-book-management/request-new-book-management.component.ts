import { Component, OnInit } from '@angular/core';
import { BookService } from '../book-management/shared/services/book.services';
import { MatDialog } from '@angular/material';
import { BookDetailComponent } from '../book-management/book-detail/book-detail.component';

@Component({
  selector: 'app-request-new-book-management',
  templateUrl: './request-new-book-management.component.html',
  styleUrls: ['./request-new-book-management.component.css']
})
export class RequestNewBookManagementComponent implements OnInit {

  displayedColumns: string[] = ['no', 'user', 'bookName', 'author','reason','action'];
  dataSource ;

  constructor(public service: BookService,public dialog: MatDialog) { }

  ngOnInit() {
    this.dataSource = this.service.NEWBOOK
  }
  openLogin(): void {
    const dialogRef = this.dialog.open(BookDetailComponent, {
      width: '400px',
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result.status == 'ok') {

      }
    });
  }

}

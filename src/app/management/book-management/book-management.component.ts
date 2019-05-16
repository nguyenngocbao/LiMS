import { Component, OnInit } from '@angular/core';
import { BookService } from './shared/services/book.services';
import { MatDialog } from '@angular/material';
import { BookDetailComponent } from './book-detail/book-detail.component';

@Component({
  selector: 'app-book-management',
  templateUrl: './book-management.component.html',
  styleUrls: ['./book-management.component.css']
})
export class BookManagementComponent implements OnInit {
  
  
  displayedColumns: string[] = ['no', 'name', 'quantity', 'type','author','action'];
  dataSource ;

  constructor(public service: BookService,public dialog: MatDialog) { }

  ngOnInit() {
    this.dataSource = this.service.getBook();
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

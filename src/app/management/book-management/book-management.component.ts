import { Component, OnInit } from '@angular/core';
import { BookService } from './shared/services/book.services';
import { MatDialog } from '@angular/material';
import { BookDetailComponent } from './book-detail/book-detail.component';
import { viewAttached } from '@angular/core/src/render3/instructions';

@Component({
  selector: 'app-book-management',
  templateUrl: './book-management.component.html',
  styleUrls: ['./book-management.component.css']
})
export class BookManagementComponent implements OnInit {


  displayedColumns: string[] = ['no', 'name', 'quantity', 'type', 'author', 'action'];
  dataSource;

  constructor(public service: BookService, public dialog: MatDialog) { }

  ngOnInit() {
    this.dataSource = this.service.getBook();
  }
  openDialog(view, id): void {
    const dialogRef = this.dialog.open(BookDetailComponent, {
      width: '80%', data: {
        view: view,
        id: id
      }
    });

    dialogRef.afterClosed().subscribe(result => {
     
    });
  }

}

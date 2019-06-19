import { Component, OnInit } from '@angular/core';
import { BookService } from '../book-management/shared/services/book.services';
import { MatDialog } from '@angular/material';
import { BookDetailComponent } from '../book-management/book-detail/book-detail.component';
import { NewBookService } from 'src/app/shared/services/newbook.service';

@Component({
  selector: 'app-request-new-book-management',
  templateUrl: './request-new-book-management.component.html',
  styleUrls: ['./request-new-book-management.component.css']
})
export class RequestNewBookManagementComponent implements OnInit {

  displayedColumns: string[] = ['no', 'user', 'bookName', 'author','reason','action'];
  dataSource ;

  constructor(public service: NewBookService) { }

  ngOnInit() {
   this.loadRequest()
  }
 
    loadRequest(){
      this.service.loadRequest().subscribe(data =>{
        this.dataSource = data.content;
      })
    }
    

}

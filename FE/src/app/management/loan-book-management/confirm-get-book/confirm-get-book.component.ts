import { Component, OnInit } from '@angular/core';
import { BookService } from '../../book-management/shared/services/book.services';
import { MatDialog } from '@angular/material';
import { LoanBookService } from '../../book-management/shared/services/loanBook.services';

@Component({
  selector: 'app-confirm-get-book',
  templateUrl: './confirm-get-book.component.html',
  styleUrls: ['./confirm-get-book.component.css']
})
export class ConfirmGetBookComponent implements OnInit {
  

  
  displayedColumns: string[] = ['no', 'book', 'user', 'dateRequest','action'];
  dataSource ;

  constructor(public service:LoanBookService,public dialog: MatDialog) { }

  ngOnInit() {
    //this.dataSource = this.service.getLoanBook();
    this.loadRequest()
  }
  loadRequest(){
    this.service.loadRequestAccept().subscribe(data =>{
      this.dataSource = data;
    })
  }
  openDialog( data): void {
   
  }
  confirmGetBook(id){
    this.service.confirmGetBook(id).subscribe(data =>{},err=>{},()=>{this.loadRequest()})

  }
  refesh(): any {
   this.loadRequest()
  }


}

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
  length = 0
  pageSize = 10
  pageSizeOptions : number[] = [5, 10, 25, 100]
  pageIndex = 0
  filter = 'name'
  search = '';
  constructor(public service:LoanBookService,public dialog: MatDialog) { }

  ngOnInit() {
    //this.dataSource = this.service.getLoanBook();
    this.loadRequest()
  }
  loadRequest(pageInfo ?){
    let data = {
      filter: this.filter,
      search: this.search,
      size: pageInfo ? pageInfo.pageSize : this.pageSize,
      page: pageInfo ? pageInfo.pageIndex : this.pageIndex
    }
    this.service.loadRequestAccept(data).subscribe(data =>{
      this.dataSource = data.content;
      this.length = data.totalElements
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
  searchBook(){
    this.loadRequest()
  }
  changePage(event){
    this.loadRequest(event)
  }

}

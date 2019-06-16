import { Component, OnInit } from '@angular/core';
import { BookService } from '../../book-management/shared/services/book.services';
import { MatDialog } from '@angular/material';

@Component({
  selector: 'app-return-book',
  templateUrl: './return-book.component.html',
  styleUrls: ['./return-book.component.css']
})
export class ReturnBookComponent implements OnInit {

  displayedColumns: string[] = ['no', 'book', 'user', 'dateRequest','action'];
  dataSource ;

  constructor(public service: BookService,public dialog: MatDialog) { }

  ngOnInit() {
    //this.dataSource = this.service.getLoanBook();
    this.loadRequest()
  }
  loadRequest(){
    this.service.loadRequestReturning().subscribe(data =>{
      this.dataSource = data;
    })
  }
  openDialog( data): void {
   
  }
  confirmReturnBook(id){
    this.service.confirmReturn(id).subscribe(data =>{},err=>{},()=>{this.loadRequest()})

  }

}

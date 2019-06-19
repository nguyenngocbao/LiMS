import { Component, OnInit } from '@angular/core';
import { BookService } from '../../book-management/shared/services/book.services';
import { MatDialog } from '@angular/material';
import { ViewBookStatusComponent } from '../view-book-status/view-book-status.component';
import { LoanBookService } from '../../book-management/shared/services/loanBook.services';
import { AbtractComponents } from 'src/app/shared/utils/AbtractComponents';
import { ToastrManager } from 'ng6-toastr-notifications';

@Component({
  selector: 'app-request-loan-book',
  templateUrl: './request-loan-book.component.html',
  styleUrls: ['./request-loan-book.component.css']
})
export class RequestLoanBookComponent extends AbtractComponents implements OnInit {

  displayedColumns: string[] = ['no', 'book', 'user', 'dateRequest','action'];
  dataSource ;

  constructor(public service: LoanBookService,public dialog: MatDialog, public toar: ToastrManager) {
    super(toar)
  }

  ngOnInit() {
    //this.dataSource = this.service.getLoanBook();
    this.loadRequest()
  }
  loadRequest(){
    this.service.loadRequest().subscribe(data =>{
      this.dataSource = data;
    })
  }
  openDialog( data): void {
    const dialogRef = this.dialog.open(ViewBookStatusComponent, {
      width: '80vw', data: data
    });

    dialogRef.afterClosed().subscribe(result => {
     this.refesh()
    });
  }
  refesh(){
    this.loadRequest()
  }

}

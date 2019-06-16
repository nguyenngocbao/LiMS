import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material';
import { BookService } from '../book-management/shared/services/book.services';
import { BookDetailComponent } from '../book-management/book-detail/book-detail.component';
import { LoanBookService } from '../book-management/shared/services/loanBook.services';
import { RequestLoanBookComponent } from './request-loan-book/request-loan-book.component';
import { ConfirmGetBookComponent } from './confirm-get-book/confirm-get-book.component';
import { ReturnBookComponent } from './return-book/return-book.component';
import { LoaningBookComponent } from './loaning-book/loaning-book.component';

@Component({
  selector: 'app-loan-book-management',
  templateUrl: './loan-book-management.component.html',
  styleUrls: ['./loan-book-management.component.css']
})
export class LoanBookManagementComponent implements OnInit {
  @ViewChild(RequestLoanBookComponent)
  requestTab: RequestLoanBookComponent
  @ViewChild(ConfirmGetBookComponent)
  confirmAcceptTab: RequestLoanBookComponent
  @ViewChild(ReturnBookComponent)
  returingTab: ReturnBookComponent
  @ViewChild(LoaningBookComponent)
  loaningTab: LoaningBookComponent

  constructor(public service: LoanBookService, public dialog: MatDialog) { }

  ngOnInit() {
  }
  changeTab(e) {
    switch (e) {
      case 0:
        this.requestTab.refesh()
        break;
      case 1:
      this.confirmAcceptTab.refesh()
        break;
      case 2:
      this.loaningTab.refesh()
        break;
      case 3:
      this.returingTab.refesh()
     
        break;

      default:
        break;
    }
  }

}

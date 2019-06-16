import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ManagementRoutingModule } from './management-routing.module';
import { BookManagementComponent } from './book-management/book-management.component';
import { MaterialModule } from '../shared/material.module';
import { BookService } from './book-management/shared/services/book.services';
import { BookDetailComponent } from './book-management/book-detail/book-detail.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoanBookManagementComponent } from './loan-book-management/loan-book-management.component';
import { RequestLoanBookComponent } from './loan-book-management/request-loan-book/request-loan-book.component';
import { LoaningBookComponent } from './loan-book-management/loaning-book/loaning-book.component';
import { ReturnBookComponent } from './loan-book-management/return-book/return-book.component';
import { RequestNewBookManagementComponent } from './request-new-book-management/request-new-book-management.component';
import { StatisticComponent } from './statistic/statistic.component';
import { ViewBookStatusComponent } from './loan-book-management/view-book-status/view-book-status.component';
import { RejectFormComponent } from './loan-book-management/view-book-status/reject-form/reject-form.component';
import { ConfirmGetBookComponent } from './loan-book-management/confirm-get-book/confirm-get-book.component';

@NgModule({
  declarations: [BookManagementComponent, BookDetailComponent, LoanBookManagementComponent, RequestLoanBookComponent, LoaningBookComponent, ReturnBookComponent, RequestNewBookManagementComponent, StatisticComponent, ViewBookStatusComponent, RejectFormComponent, ConfirmGetBookComponent],
  imports: [
    CommonModule,
    ManagementRoutingModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
  ]
  , providers: [BookService],
  entryComponents: [BookDetailComponent,ViewBookStatusComponent]
})
export class ManagementModule { }

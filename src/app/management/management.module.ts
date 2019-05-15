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

@NgModule({
  declarations: [BookManagementComponent, BookDetailComponent, LoanBookManagementComponent, RequestLoanBookComponent, LoaningBookComponent, ReturnBookComponent],
  imports: [
    CommonModule,
    ManagementRoutingModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
  ]
  , providers: [BookService],
  entryComponents: [BookDetailComponent]
})
export class ManagementModule { }

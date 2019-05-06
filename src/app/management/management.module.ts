import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ManagementRoutingModule } from './management-routing.module';
import { BookManagementComponent } from './book-management/book-management.component';
import { MaterialModule } from '../shared/material.module';
import { BookService } from './book-management/shared/services/book.services';
import { BookDetailComponent } from './book-management/book-detail/book-detail.component';

@NgModule({
  declarations: [BookManagementComponent, BookDetailComponent],
  imports: [
    CommonModule,
    ManagementRoutingModule,
    MaterialModule
  ]
  ,providers: [BookService]
})
export class ManagementModule { }

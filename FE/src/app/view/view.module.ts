import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ViewRoutingModule } from './view-routing.module';
import { DashboardComponent } from './dashboard/dashboard.component';
import { MaterialModule } from '../shared/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { StatusPersonalComponent } from './status-personal/status-personal.component';
import { StatusRequestLoanComponent } from './status-personal/status-request-loan/status-request-loan.component';
import { StatusLoaningComponent } from './status-personal/status-loaning/status-loaning.component';
import { StatusReserveComponent } from './status-personal/status-reserve/status-reserve.component';
import { StatusReturnedComponent } from './status-personal/status-returned/status-returned.component';
import { BookService } from '../management/book-management/shared/services/book.services';
import { ViewService } from '../services/view.service';
import { RequestNewBookComponent } from './request-new-book/request-new-book.component';

@NgModule({
  declarations: [DashboardComponent, StatusPersonalComponent, StatusRequestLoanComponent, StatusLoaningComponent, StatusReserveComponent, StatusReturnedComponent, RequestNewBookComponent],
  imports: [
    CommonModule,
    ViewRoutingModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
  ], providers: [BookService,ViewService],
})
export class ViewModule { }

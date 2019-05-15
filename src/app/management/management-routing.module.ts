import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BookManagementComponent } from './book-management/book-management.component';
import { LoanBookManagementComponent } from './loan-book-management/loan-book-management.component';

const routes: Routes = [
  {
    path: 'book',
    component: BookManagementComponent
  },
  {
    path: 'loan',
    component: LoanBookManagementComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ManagementRoutingModule { }

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BookManagementComponent } from './book-management/book-management.component';
import { LoanBookManagementComponent } from './loan-book-management/loan-book-management.component';
import { RequestNewBookManagementComponent } from './request-new-book-management/request-new-book-management.component';
import { StatisticComponent } from './statistic/statistic.component';
import { CategoryListComponent } from './category-management/category-list/category-list.component';
import { UserListComponent } from './user-management/user-list/user-list.component';

const routes: Routes = [
  {
    path: 'category-list',
    component: CategoryListComponent
  },
  {
    path: 'book',
    component: BookManagementComponent
  },
  {
    path: 'loan',
    component: LoanBookManagementComponent
  },
  {
    path: 'newBook',
    component: RequestNewBookManagementComponent
  },
  {
    path: 'statistic',
    component: StatisticComponent
  },
  {path: 'user',
  component: UserListComponent
}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ManagementRoutingModule { }

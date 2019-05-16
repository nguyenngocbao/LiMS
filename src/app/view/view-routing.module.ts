import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { StatusPersonalComponent } from './status-personal/status-personal.component';
import { StatusReturnedComponent } from './status-personal/status-returned/status-returned.component';

const routes: Routes = [
  {
    path: '',
    component: DashboardComponent
  }
  ,
  {
    path: 'status',
    component: StatusPersonalComponent
  }
  ,
  {
    path: 'history',
    component: StatusReturnedComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ViewRoutingModule { }

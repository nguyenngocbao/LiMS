import { NgModule } from '@angular/core'
import { Routes, RouterModule } from '@angular/router'

const routes: Routes = [
  {
    path: 'book',
    loadChildren: '../app/management/management.module#ManagementModule'
  },
  {
    path: '',
    loadChildren: '../app/view/view.module#ViewModule'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

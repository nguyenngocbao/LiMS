import { NgModule } from '@angular/core'
import { Routes, RouterModule } from '@angular/router'
import { AuthService } from './services/auth.service';

const routes: Routes = [
  {
    path: 'management',
    loadChildren: '../app/management/management.module#ManagementModule',
    canActivate: [AuthService]
  },
  {
    path: '',
    loadChildren: '../app/view/view.module#ViewModule'
  },
  {
    path: 'home',
    loadChildren: '../app/view/view.module#ViewModule'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

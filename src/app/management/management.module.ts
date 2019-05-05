import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ManagementRoutingModule } from './management-routing.module';
import { BookManagementComponent } from './book-management/book-management.component';
import { MaterialModule } from '../shared/material.module';

@NgModule({
  declarations: [BookManagementComponent],
  imports: [
    CommonModule,
    ManagementRoutingModule,
    MaterialModule
  ]
})
export class ManagementModule { }

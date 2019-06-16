import { ToastrService } from 'ngx-toastr';
import { Component, OnInit } from '@angular/core';
import { ViewService } from 'src/app/shared/services/view.service';
import { ConfirmComponent } from 'src/app/shared/components/confirm/confirm.component';
import { MatDialog } from '@angular/material';
import { Subscription } from 'rxjs';
import { environment } from 'src/environments/environment.prod';
import { ConfirmType } from 'src/app/shared/utils/ConfirmType';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  constructor(private service: ViewService,public dialog: MatDialog) { }
  books = []
  length = 0
  pageSize = 6
  pageSizeOptions : number[] = [6, 12, 24]
  pageIndex = 0
  loadBookSubscription: Subscription

  ngOnInit() {
    this.loadBook()
  }
  loadBook() {
    if (this.loadBookSubscription) {
      this.loadBookSubscription.unsubscribe()
    }
    let data = {
      page: this.pageIndex,
      size: this.pageSize
    }
    this.loadBookSubscription = this.service.listBooks(data).subscribe(rs => {
      this.books = rs.content
      this.length = rs.totalElements
     
    })
  }
  openLoanBook(item){
    const dialogRef = this.dialog.open(ConfirmComponent, {
      width: '500px',
      data: { item:item,type: ConfirmType.DASHBOARD }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result.status == 'ok') {

      }
    });

  }

  changePage(_) {
    this.loadBook()
  }

}

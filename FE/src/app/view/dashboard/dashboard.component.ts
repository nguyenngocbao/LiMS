import { ToastrService } from 'ngx-toastr';
import { Component, OnInit } from '@angular/core';
import { ViewService } from 'src/app/shared/services/view.service';
import { ConfirmComponent } from 'src/app/shared/components/confirm/confirm.component';
import { MatDialog } from '@angular/material';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  constructor(private service: ViewService,public dialog: MatDialog) { }
  books = []
  length = 0
  pageSize = 10
  pageSizeOptions : number[] = [5, 10, 25, 100]
  pageIndex = 0

  ngOnInit() {
    this.loadBook()
  }
  loadBook() {
    this.service.getBooks().subscribe(data => {
      this.books = data.content
      console.log(data);



      
    })
  }
  openLoanBook(item){
    const dialogRef = this.dialog.open(ConfirmComponent, {
      width: '500px',
      data: { item:item }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result.status == 'ok') {

      }
    });

  }

  changePage(_) {
    this.books = this.service.getBook()
  }

}

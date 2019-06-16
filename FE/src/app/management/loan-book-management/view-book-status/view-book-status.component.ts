import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { BookService } from '../../book-management/shared/services/book.services';

@Component({
  selector: 'app-view-book-status',
  templateUrl: './view-book-status.component.html',
  styleUrls: ['./view-book-status.component.css']
})
export class ViewBookStatusComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<ViewBookStatusComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
    , public service: BookService) {
    this.status = {
      WAITING: { text: "Đang chờ phê duyệt", color: '#ffff00' },
      LOANING_ACCEPT: { text: "Đã chấp nhận", color: '#ffff00' },
      LOANING_REJECT: { text: "Đã từ chối", color: '#ffff00' }
    }
  }
  status
  displayedColumns: string[] = ['no', 'book', 'dateRequest', 'action'];
  displayedColumnBook: string[] = ['no', 'user', 'dateRequest', 'action'];
  dataSource;
  dataSourceBook;
  dataSourceUser;
  ngOnInit() {
    this.getStatusBook()
    this.getStatusUser()
  }
  getStatusBook() {
    this.service.getRequestByBook(this.data.book.id).subscribe(
      data => {
        this.dataSourceBook = data
        this.getStatus(data)
      }
    )


  }
  request
  remain
  getStatus(data: any) {
    this.request = data.length;
    this.remain = data.findIndex(element => {
      return element.status == 'WAITING'
    })


  }
  getStatusUser() {
    this.service.getRequestByUser(this.data.user.id).subscribe(
      data => {
        this.dataSourceUser = data
      }
    )

  }
  onAccept() {
    this.service.loaningAccept(this.data.id).subscribe(
      data => {

      }, err => { }, () => {
        const data = { status: 'ok' }
        this.dialogRef.close(data);
      }
    )

  }
  onReject(reason) {
    this.service.loaningReject(this.data.id, reason).subscribe(
      data => {

      }, err => { }, () => {
        const data = { status: 'ok' }
        this.dialogRef.close(data);
      }
    )

  }
  onCancel() {
    const data = { status: 'no' }
    this.dialogRef.close(data);

  }

}

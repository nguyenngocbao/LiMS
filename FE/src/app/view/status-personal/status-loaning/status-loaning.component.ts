import { Component, OnInit } from '@angular/core';
import { ViewService } from 'src/app/shared/services/view.service';
import { MatDialog } from '@angular/material';
import { ConfirmComponent } from 'src/app/shared/components/confirm/confirm.component';
import { ConfirmType } from 'src/app/shared/utils/ConfirmType';
import { AbtractComponents } from 'src/app/shared/utils/AbtractComponents';
import { ToastrManager } from 'ng6-toastr-notifications';

@Component({
  selector: 'app-status-loaning',
  templateUrl: './status-loaning.component.html',
  styleUrls: ['./status-loaning.component.css']
})
export class StatusLoaningComponent extends AbtractComponents implements OnInit {
  data;
  status
  constructor(private service: ViewService, public dialog: MatDialog, toa: ToastrManager) {
    super(toa)
  }
  ngOnInit() {
    this.loadRequest()
  }
  onDelete(id) {
    console.log(id)
    this.delete(id);
  }
  loadRequest() {
    this.service.loadLoaning().subscribe(data => {
      this.data = data
    })
  }
  delete(id) {
    this.service.deleteRequest(id).subscribe(data => {

    }, err => { }, () => {
      this.loadRequest()
    })
  }
  returnBook(id) {
    this.service.returnBooks(id).subscribe(data => {
      this.notifySucccess("Gửi yêu cầu trả sách thành công!")
    }, err => {
      this.notifyError('Gửi yêu cầu thất bại')
    }, () => {
      this.loadRequest()
    })

  }
  openConfirm(item: any) {
    const dialogRef = this.dialog.open(ConfirmComponent, {
      width: '500px',
      data: { item: item.book, type: ConfirmType.RETURN }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result.status == 'ok') {
        this.returnBook(item.id)
      }
    });

  }

}

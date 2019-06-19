import { Component, OnInit } from '@angular/core';
import { ViewService } from 'src/app/shared/services/view.service';
import { ConfirmComponent } from 'src/app/shared/components/confirm/confirm.component';
import { ConfirmType } from 'src/app/shared/utils/ConfirmType';
import { MatDialog } from '@angular/material';
import { AbtractComponents } from 'src/app/shared/utils/AbtractComponents';
import { ToastrManager } from 'ng6-toastr-notifications';

@Component({
  selector: 'app-status-reserve',
  templateUrl: './status-reserve.component.html',
  styleUrls: ['./status-reserve.component.css']
})
export class StatusReserveComponent extends AbtractComponents implements OnInit {
  data;
  status
  constructor(private service: ViewService, public dialog: MatDialog, private toa: ToastrManager) {
    super(toa)
    this.status = {
      RESERVE: { text: "Đang đặt trước", color: '#ffff00' },
    }
  }
  ngOnInit() {
    this.loadRequest()
  }
  onDelete(id) {
    console.log(id)
    this.delete(id);
  }
  loadRequest() {
    this.service.loadReserve().subscribe(data => {
      this.data = data
    })
  }
  refesh() {
    this.loadRequest()
  }
  delete(id) {
    this.service.deleteRequest(id).subscribe(data => {
      this.notifySucccess('Hủy yêu cầu thành công')
    }, err => {
      this.notifyError('Hủy yêu cầu thất bại')
    }, () => {
      this.loadRequest()
    })
  }

  openConfirm(item) {
    const dialogRef = this.dialog.open(ConfirmComponent, {
      width: '500px',
      data: { item: item.book, type: ConfirmType.CANCEL }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result.status == 'ok') {
        this.delete(item.id)
      }
    });

  }

}

import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { ViewService } from '../../services/view.service';
import { ConfirmType } from '../../utils/ConfirmType';
import { AbtractComponents } from '../../utils/AbtractComponents';
import { ToastrManager } from 'ng6-toastr-notifications';

@Component({
  selector: 'app-confirm',
  templateUrl: './confirm.component.html',
  styleUrls: ['./confirm.component.css']
})
export class ConfirmComponent extends AbtractComponents implements OnInit {

  constructor(public service: ViewService,
    public dialogRef: MatDialogRef<ConfirmComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, private toar: ToastrManager) {
    super(toar)
  }
  content = ''
  ngOnInit() {
    this.setContent()

  }
  setContent() {
    switch (this.data.type) {
      case ConfirmType.DASHBOARD:
        this.content = 'Bạn muốn mượn quyển sách này?'
        break;
      case ConfirmType.RESERVE:
        this.content = 'Bạn muốn đặt trước quyển sách này?'
        break;
      case ConfirmType.RETURN:
        this.content = 'Bạn muốn trả quyển sách này?'
        break;
      case ConfirmType.CANCEL:
        this.content = 'Bạn muốn bỏ lựa chọn này?'
        break;

      default:
        break;
    }
  }
  /*ACTION */
  onCancel(): void {
    const data = { status: 'no' }
    this.dialogRef.close(data);
  }
  onSubmit(): void {
    switch (this.data.type) {
      case ConfirmType.DASHBOARD:
        this.loanBook()
        break;
      case ConfirmType.RESERVE:
        this.reserveBook()
        break;
      default:
        const data = { status: 'ok' }
        this.dialogRef.close(data);
        break;
    }

  }
  loanBook() {
    this.service.loanBooks(this.data.item.id).subscribe(data => { this.notifySucccess('Bạn đã mượn sách thành công!') },
      err => {
        this.notifyError('Mượn thất bại với lý do: ' + err.error)
      },
      () => {
        const data = { status: 'ok' }
        this.dialogRef.close(data);
      })

  }
  reserveBook() {
    this.service.reserveBooks(this.data.item.id).subscribe(data => { this.notifySucccess('Bạn đã đặt trước sách thành công!') },
      err => {
        this.notifyError('Đặt trước thất bại với lý do: ' + err.error)
      },
      () => {
        const data = { status: 'ok' }
        this.dialogRef.close(data);
      })
  }
  returnBook() {
    this.service.returnBooks(this.data.id).subscribe(data => { this.notifySucccess('Gửi yêu cầu thành công!') },
      err => {
        this.notifyError('Trả sách thất bại với lý do: ' + err.error)
      },
      () => { })
  }


}

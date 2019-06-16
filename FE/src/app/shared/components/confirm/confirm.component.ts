import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { ViewService } from '../../services/view.service';

@Component({
  selector: 'app-confirm',
  templateUrl: './confirm.component.html',
  styleUrls: ['./confirm.component.css']
})
export class ConfirmComponent implements OnInit {

  constructor(public service: ViewService, public dialogRef: MatDialogRef<ConfirmComponent>, @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit() {
  }
  /*ACTION */
  onCancel(): void {
    const data = { status: 'no' }
    this.dialogRef.close(data);
  }
  onSubmit(): void {
    this.loanBook()

  }
  loanBook() {
    this.service.loanBooks(this.data.item.id).subscribe(data => { console.log(data) },
      err => { },
      () => {
        const data = { status: 'ok' }
        this.dialogRef.close(data);
      })

  }
  reserveBook() {
    this.service.reserveBooks(this.data.id).subscribe(data => { console.log(data) },
      err => { },
      () => { })
  }
  returnBook() {
    this.service.returnBooks(this.data.id).subscribe(data => { console.log(data) },
      err => { },
      () => { })
  }


}

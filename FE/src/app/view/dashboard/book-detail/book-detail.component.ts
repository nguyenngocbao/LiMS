import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-book-detail',
  templateUrl: './book-detail.component.html',
  styleUrls: ['./book-detail.component.css']
})
export class BookDetailComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<BookDetailComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit() {
  }
  onSubmit() {
    const data = { status: 'ok' }
    this.dialogRef.close(data);

  }
  onCancel() {
    const data = { status: 'no' }
    this.dialogRef.close(data);

  }

}

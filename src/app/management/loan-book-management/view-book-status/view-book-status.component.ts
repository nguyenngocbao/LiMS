import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material';

@Component({
  selector: 'app-view-book-status',
  templateUrl: './view-book-status.component.html',
  styleUrls: ['./view-book-status.component.css']
})
export class ViewBookStatusComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<ViewBookStatusComponent>,) { }

  ngOnInit() {
  }

}

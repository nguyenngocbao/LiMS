import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import * as $ from 'jquery';
@Component({
  selector: 'app-book-detail',
  templateUrl: './book-detail.component.html',
  styleUrls: ['./book-detail.component.css']
})
export class BookDetailComponent implements OnInit {

  form: FormGroup;
 abc
  // upload file
  selectedFile: File;
  fileName = '';
  imgsrc
  edit = true
  constructor(public dialogRef: MatDialogRef<BookDetailComponent>, @Inject(MAT_DIALOG_DATA) public data: any, private fb: FormBuilder) { }

  ngOnInit() {
    this.initForm()
  }
  /*FORM */
  initForm() {
    this.form = this.fb.group({
      name: ['', [Validators.required]],
      image: ['', [Validators.required]],
      quantity: ['', [Validators.required]],
      type: ['', [Validators.required]],
      author:['', [Validators.required]]
    })
  }
  /*ACTION */
  onCancel(): void {
    const data = {status: 'no'}
    this.dialogRef.close(data);
  }
  onSubmit(): void {
    //this.uploadFile(this.form.get('name').value);
   const data = {status: 'ok'}
     this.dialogRef.close(data);
    
  }
  /*Handle file */
  onFileSelected(event) {
    this.selectedFile = <File>event.target.files[0];
    this.form.get('image').setValue(this.selectedFile.name.split('.')[0]);
    if (this.selectedFile.size <= 1000000) {
      const reader = new FileReader();
      if (event.target.files && event.target.files.length > 0) {
          reader.onload = function (e: any) {
              $('#avatar').attr('src', e.target.result);
              // console.log($('#avatar').attr('src'));
          };
          reader.readAsDataURL(this.selectedFile);
      }
  } else {
      
      $('#avatar').attr('src', this.imgsrc);
  }

  }
  



}

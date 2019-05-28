import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import * as $ from 'jquery';
import { BookService } from '../shared/services/book.services';
@Component({
  selector: 'app-book-detail',
  templateUrl: './book-detail.component.html',
  styleUrls: ['./book-detail.component.css']
})
export class BookDetailComponent implements OnInit {

  form: FormGroup;
  title = 'Thêm'
  // upload file
  selectedFile: File;
  fileName = '';
  imgsrc
  view = 1
  edit = true
  info
  constructor(public dialogRef: MatDialogRef<BookDetailComponent>, @Inject(MAT_DIALOG_DATA) public data: any, private fb: FormBuilder, public service: BookService) { }

  ngOnInit() {
    this.initView(this.data.view, this.data.id)

  }
  initView(view, id) {
    this.view = view
    switch (view) {
      case 1:
        this.title = 'Thêm'
        this.info = {id: 1,name: '',image: '', quantity: '',type: '', author: '',description: '',publisher:''}

        break;
      case 2:
        this.title = 'Sửa'
        this.info = this.service.BOOKS[id - 1]
        this.imgsrc = this.info.image

        break;
      case 3:
        this.info = this.service.BOOKS[id - 1]
        this.title = 'Chi tiết'
        this.imgsrc = this.info.image

        break;

      default:
        break;
    }
    this.initForm()
  }

  /*FORM */
  initForm() {
    this.form = this.fb.group({
      name: [this.info.name, [Validators.required]],
      image: [this.info.image, [Validators.required]],
      quantity: [this.info.quantty, [Validators.required]],
      description: [this.info.description, [Validators.required]],
      author: [this.info.author, [Validators.required]],
      publisher: [this.info.publisher, [Validators.required]],
      catalogy: [this.info.type, [Validators.required]]
    })
  }
  /*ACTION */
  onCancel(): void {
    const data = { status: 'no' }
    this.dialogRef.close(data);
  }
  onSubmit(): void {
    //this.uploadFile(this.form.get('name').value);
    const data = { status: 'ok' }
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

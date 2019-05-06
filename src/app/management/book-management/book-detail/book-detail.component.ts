import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-book-detail',
  templateUrl: './book-detail.component.html',
  styleUrls: ['./book-detail.component.css']
})
export class BookDetailComponent implements OnInit {

  form: FormGroup;
 

  // upload file
  selectedFile: File;
  fileName = '';
  constructor(public dialogRef: MatDialogRef<BookDetailComponent>, @Inject(MAT_DIALOG_DATA) public data: any, private fb: FormBuilder) { }

  ngOnInit() {
    this.initForm()
  }
  /*FORM */
  initForm() {
    this.form = this.fb.group({
      name: [this.data.name, [Validators.required]],
      version: [this.data.version, [Validators.required]],
      fileName: [this.fileName, [Validators.required]]
    })
  }
  /*ACTION */
  onCancel(): void {
   // const data = {status: 'no'}
    //this.dialogRef.close(data);
  }
  onSubmit(): void {
    //this.uploadFile(this.form.get('name').value);
   // const data = {status: 'ok'}
     // this.dialogRef.close(data);
    
  }
  /*Handle file */
  onFileSelected(event) {
    this.selectedFile = <File>event.target.files[0];
    this.form.get('fileName').setValue(this.selectedFile.name.split('.')[0]);

  }
  



}

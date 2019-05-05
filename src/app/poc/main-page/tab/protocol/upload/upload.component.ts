import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.css']
})
export class UploadComponent implements OnInit {
  form: FormGroup;
 

  // upload file
  selectedFile: File;
  fileName = '';
  constructor(public dialogRef: MatDialogRef<UploadComponent>, @Inject(MAT_DIALOG_DATA) public data: any, private fb: FormBuilder) { }

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
    this.dialogRef.close();
  }
  onSubmit(): void {
    const data = {status: 'ok'}
    this.dialogRef.close(data);
    
  }
  /*Handle file */
  onFileSelected(event) {
    this.selectedFile = <File>event.target.files[0];
    this.form.get('fileName').setValue(this.selectedFile.name.split('.')[0]);

  }
  

}

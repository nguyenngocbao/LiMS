import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form: FormGroup;

  constructor(public dialogRef: MatDialogRef<LoginComponent>, @Inject(MAT_DIALOG_DATA) public data: any, private fb: FormBuilder) { }

  ngOnInit() {
    this.initForm()
  }
  /*FORM */
  initForm() {
    this.form = this.fb.group({
      name: ['', [Validators.required]],
      password: ['', [Validators.required]]
    })
  }
  /*ACTION */
  onCancel(): void {

  }
  onSubmit(): void {



  }

}

import { UserService } from './../services/user.service';
import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form: FormGroup;
  loginSubscription: Subscription

  constructor(public dialogRef: MatDialogRef<LoginComponent>, @Inject(MAT_DIALOG_DATA) public data: any, private fb: FormBuilder,
    private userService: UserService, private toastrService: ToastrService) { }

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
    this.onClose()
  }

  onClose(): void {
    this.dialogRef.close();
}
  onSubmit(): void {
    this.toastrService.success('Login successfully')
    localStorage.removeItem('token')
    if (this.loginSubscription) {
      this.loginSubscription.unsubscribe()
    }
    this.loginSubscription = this.userService.login(this.form.controls.name.value, this.form.controls.password.value)
    .subscribe((rs) => {
      this.onClose()
      localStorage.setItem('token', rs.token)
      this.toastrService.success('Login successfully')
    }, (_) => {
      this.onClose()
      this.toastrService.error('Login failed')
    })

  }

}


import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';

import { AbtractComponents } from '../shared/utils/AbtractComponents';
import { ToastrManager } from 'ng6-toastr-notifications';
import { UserService } from '../shared/services/user.service';
import { ShareService } from '../services/share.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent extends AbtractComponents implements OnInit {

  form: FormGroup;
  loginSubscription: Subscription

  constructor(public dialogRef: MatDialogRef<LoginComponent>, @Inject(MAT_DIALOG_DATA) public data: any, private fb: FormBuilder,
    private userService: UserService,public toastr: ToastrManager) { 
      super(toastr)
    }

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
    localStorage.removeItem('token')
    if (this.loginSubscription) {
      this.loginSubscription.unsubscribe()
    }
    this.loginSubscription = this.userService.login(this.form.controls.name.value, this.form.controls.password.value)
    .subscribe((rs) => {
      this.onClose()
      localStorage.setItem('token', rs.token)
      localStorage.setItem('fullName', rs.fullName)
      this.notifySucccess('Login successfully')
      ShareService.loginEvent.emit("login")
    }, (_) => {
      this.notifyError('Login failed')
    })

  }

}

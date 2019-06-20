
import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog } from '@angular/material';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';

import { AbtractComponents } from '../shared/utils/AbtractComponents';
import { ToastrManager } from 'ng6-toastr-notifications';
import { UserService } from '../shared/services/user.service';
import { ShareService } from '../services/share.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html'
})
export class ChangePasswordComponent extends AbtractComponents implements OnInit {

  form: FormGroup;
  loginSubscription: Subscription

  constructor(public dialogRef: MatDialogRef<ChangePasswordComponent>, @Inject(MAT_DIALOG_DATA) public data: any, private fb: FormBuilder,
    private userService: UserService,public toastr: ToastrManager,
    public dialog: MatDialog) { 
      super(toastr)
    }

  ngOnInit() {
    this.initForm()

  }
  /*FORM */
  initForm() {
    this.form = this.fb.group({
      oldPassword: ['', [Validators.required, Validators.minLength(6)]],
      newPassword: ['', [Validators.required, Validators.minLength(6),]],
      retypePassword: ['', [Validators.required, Validators.minLength(6),]]
    },
    this.passwordMatchValidator)
  }
  /*ACTION */
  onCancel(): void {
    this.onClose()
  }

  onClose(): void {
    this.dialogRef.close();
}
  onSubmit(): void {
    if (this.loginSubscription) {
      this.loginSubscription.unsubscribe()
    }
    this.loginSubscription = this.userService.changePassword(this.form.value)
    .subscribe((_) => {
      this.onClose()
      this.notifySucccess('Thay đổi mật khẩu thành công')
    }, (err) => {
      this.notifyError(err && err.error ? err.error : 'Thay đổi mật khẩu không thành công')
    })

  }

  passwordMatchValidator(g: FormGroup) {
        return g.get('newPassword').value === g.get('retypePassword').value
            ? null : { 'mismatch': true };
    }

}

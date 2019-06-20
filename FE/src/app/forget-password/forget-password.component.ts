
import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog } from '@angular/material';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';

import { AbtractComponents } from '../shared/utils/AbtractComponents';
import { ToastrManager } from 'ng6-toastr-notifications';
import { UserService } from '../shared/services/user.service';
import { ShareService } from '../services/share.service';

@Component({
  selector: 'app-forget-password',
  templateUrl: './forget-password.component.html'
})
export class ForgetPasswordComponent extends AbtractComponents implements OnInit {

  form: FormGroup;

  constructor(public dialogRef: MatDialogRef<ForgetPasswordComponent>, @Inject(MAT_DIALOG_DATA) public data: any, private fb: FormBuilder,
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
      data: ['', [Validators.required]]
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
    this.userService.forgetPassword(this.form.controls.data.value)
    .subscribe((_) => {
      this.onClose()
      this.notifySucccess('Mật khẩu đã được đổi')
    }, (err) => {
      console.log(err)
      this.notifyError(err && err.error ? err.error : 'Thay đổi mật khẩu không thành công')
    })

  }

  passwordMatchValidator(g: FormGroup) {
        return g.get('newPassword').value === g.get('retypePassword').value
            ? null : { 'mismatch': true };
    }

}

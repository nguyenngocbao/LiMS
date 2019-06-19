
import { Component, OnInit, Inject, Input } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import * as $ from 'jquery';
import { AbtractComponents } from '../shared/utils/AbtractComponents';
import { ToastrManager } from 'ng6-toastr-notifications';
import { ShareService } from '../services/share.service';
import { UserService } from '../shared/services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html'
})
export class RegisterComponent extends AbtractComponents implements OnInit {
  isAdmin:boolean = false
  form: FormGroup;
  registerSubscription: Subscription
  selectedFile: File;
  fileName = '';
  imgsrc

  roles = [{
    key: 'ADMIN',
    value: 'Quản trị'
  },
  {
    key: 'USER',
    value: 'Người dùng'
  },
  {
    key: 'TEACHER',
    value: 'Giáo viên'
  }]
  constructor(public dialogRef: MatDialogRef<RegisterComponent>, @Inject(MAT_DIALOG_DATA) public data: any, private fb: FormBuilder,
    private userService: UserService,public toastr: ToastrManager) { 
      super(toastr)
    }

  ngOnInit() {
    this.isAdmin = this.data && this.data.isAdmin
    this.initForm()

  }
  /*FORM */
  initForm() {
    if (this.isAdmin) {
      this.form = this.fb.group({
        username: ['', [Validators.required]],
        password: ['', [Validators.required]],
        retypePassword: ['', [Validators.required]],
        email: ['', [Validators.required, Validators.pattern('[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@\\S+')]],
        fullName: ['', [Validators.required]],
        role: ['', [Validators.required]]
      })
    } else {

      this.form = this.fb.group({
        username: ['', [Validators.required]],
        password: ['', [Validators.required]],
        retypePassword: ['', [Validators.required]],
        email: ['', [Validators.required, Validators.pattern('[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@\\S+')]],
        fullName: ['', [Validators.required]],
        pathImages: ['', [Validators.required]]
      },
      this.passwordMatchValidator)
    }
  }
  /*ACTION */
  onCancel(): void {
    this.onClose()
  }

  onClose(data?:any): void {
    this.dialogRef.close(data);
}
  onSubmit(): void {
    if (this.registerSubscription) {
      this.registerSubscription.unsubscribe()
    }
    if(!this.isAdmin) {

      this.registerSubscription = this.userService.register(this.form.value, this.selectedFile)
      .subscribe((_rs) => {
          this.notifySucccess('Đăng ký thành công')
        this.userService.login(this.form.controls.username.value, this.form.controls.password.value)
          .subscribe((rs) => {
              localStorage.setItem('token', rs.token)
              ShareService.loginEvent.emit("login")
              this.onClose()
          }, (err) => {
              this.notifyError(err.error ? err.error.message: 'Đăng ký không thành công')
          })
  
      
      }, (err) => {
          this.notifyError(err.error ? err.error.message: 'Đăng ký không thành công')
      })
    } else {
      this.registerSubscription = this.userService.registerByAdmin(this.form.value)
      .subscribe((_rs) => {
          this.notifySucccess('Đăng ký thành công')
          this.onClose({status: 'success'})
          }, (err) => {
              this.notifyError(err.error ? err.error.message: 'Đăng ký không thành công')
          })
    }

  }

  passwordMatchValidator(g: FormGroup) {
    return g.get('password').value === g.get('retypePassword').value
        ? null : { 'mismatch': true };
}

  onFileSelected(event) {
    this.selectedFile = <File>event.target.files[0];
    this.form.get('pathImages').setValue(this.selectedFile.name.split('.')[0]);
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

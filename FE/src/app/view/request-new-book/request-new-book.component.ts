import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NewBookService } from 'src/app/shared/services/newbook.service';
import { AbtractComponents } from 'src/app/shared/utils/AbtractComponents';
import { ToastrManager } from 'ng6-toastr-notifications';

@Component({
  selector: 'app-request-new-book',
  templateUrl: './request-new-book.component.html',
  styleUrls: ['./request-new-book.component.css']
})
export class RequestNewBookComponent extends AbtractComponents implements OnInit {

  form: FormGroup;
  // upload file
  selectedFile: File;
  fileName = '';
  imgsrc
  edit = true
  constructor(public service: NewBookService, private fb: FormBuilder, public toar: ToastrManager) {
    super(toar)
  }

  ngOnInit() {
    this.initForm()
  }

  /*FORM */
  initForm() {
    this.form = this.fb.group({
      bookName: ['', [Validators.required]],
      reason: ['', [Validators.required]],
      author: ['', [Validators.required]]
    })
  }
  /*ACTION */
  onCancel(): void {

  }
  onSubmit(): void {
    if (this.form.valid) {
      this.service.requestNewBook(this.form.value).subscribe(data => {
        this.notifySucccess("Yêu cầu đã được gửi")
      }, err => {
        this.notifyError("Yêu cầu thất bại")
      }, () => {

      })
    } else {
      this.notifyError('Bạn phải nhập đầy đủ dữ liệu');
    }


  }






}

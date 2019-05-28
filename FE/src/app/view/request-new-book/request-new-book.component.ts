import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-request-new-book',
  templateUrl: './request-new-book.component.html',
  styleUrls: ['./request-new-book.component.css']
})
export class RequestNewBookComponent implements OnInit {

  form: FormGroup;
  // upload file
  selectedFile: File;
  fileName = '';
  imgsrc
  edit = true
  constructor( private fb: FormBuilder) { }

  ngOnInit() {
    this.initForm()
  }
  
  /*FORM */
  initForm() {
    this.form = this.fb.group({
      name: ['', [Validators.required]],
      image: ['', [Validators.required]],
      quantity: ['', [Validators.required]],
      type: ['', [Validators.required]],
      author:['', [Validators.required]]
    })
  }
  /*ACTION */
  onCancel(): void {
   
  }
  onSubmit(): void {
   
    
  }
  

  



}

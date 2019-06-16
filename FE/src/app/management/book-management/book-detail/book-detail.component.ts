import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import * as $ from 'jquery';
import { BookService } from '../shared/services/book.services';
import { AbtractComponents } from 'src/app/shared/utils/AbtractComponents';
import { ToastrManager } from 'ng6-toastr-notifications';
import { CategoryService } from 'src/app/services/category.service';
import { environment } from 'src/environments/environment.prod';
@Component({
  selector: 'app-book-detail',
  templateUrl: './book-detail.component.html',
  styleUrls: ['./book-detail.component.css']
})
export class BookDetailComponent extends AbtractComponents implements OnInit {

  form: FormGroup;
  title = 'Thêm'
  // upload file
  selectedFile: File;
  fileName = '';
  imgsrc
  view = 1
  edit = true
  categories = []
  categorySelected: number
  constructor(public dialogRef: MatDialogRef<BookDetailComponent>, 
    @Inject(MAT_DIALOG_DATA) public data: any, private fb: FormBuilder, 
    public service: BookService,
    public toastr: ToastrManager,
    public categoryService: CategoryService) {
      super(toastr)
     }

  ngOnInit() {
    this.loadCategory()
    this.initView(this.data.view, this.data.id)

  }
  initView(view, id) {
    this.view = view
    switch (view) {
      case 1:
        this.title = 'Thêm'
        break;
      case 2:
        this.title = 'Sửa'
        this.loadBookDetail()
        break;
      case 3:
        this.title = 'Chi tiết'
        this.loadBookDetail()

        break;

      default:
        break;
    }
    this.initForm()
  }

  /*FORM */
  initForm() {
    this.form = this.fb.group({
      name: ['', [Validators.required]],
      image: ['', [Validators.required]],
      quantity: ['', [Validators.required]],
      description: ['', [Validators.required]],
      author: ['', [Validators.required]],
      publisher: ['', [Validators.required]],
      category: [this.categorySelected, [Validators.required]],
      isbn: ['', [Validators.required]]
    })
  }
  /*ACTION */
  onCancel(data): void {
    this.dialogRef.close(data);
  }
  onSubmitAdd(): void {
    //this.uploadFile(this.form.get('name').value);
    this.service.addBook(this.form.value, this.selectedFile, this.form.controls.category.value)
    .subscribe((_) => {
      this.notifySucccess('Thêm sách thành công')
    }, (err) => {
      this.notifyError(err.error ? err.error.message : 'Thêm sách không thành công')
    },()=>{
      const data = { status: 'ok' }
       this.dialogRef.close(data);
    })
    //

  }

  onSubmitEdit(): void {
    //this.uploadFile(this.form.get('name').value);
    this.service.editBook(this.form.value, this.selectedFile, this.data.id)
    .subscribe((book) => {
      this.notifySucccess('Chỉnh sửa thông tin sách thành công')
      this.onCancel({status: 'success'})
    }, (err) => {
      this.notifyError(err.error ? err.error.message : 'Chỉnh sửa thông tin sách không thành công')
    },()=>{
      const data = { status: 'ok' }
       this.dialogRef.close(data);
    })
    // 

  }
  /*Handle file */
  onFileSelected(event) {
    this.selectedFile = <File>event.target.files[0];
    this.form.get('image').setValue(this.selectedFile.name.split('.')[0]);
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

  loadCategory() {
    this.categoryService.list().subscribe((categories) => {
      this.categories = categories
    })
  }

  loadBookDetail() {
    this.service.getBookById(this.data.id)
    .subscribe((book) => {
      this.imgsrc = `${environment.API}/${book.image}`
      this.form.setValue({
        name: book.name,
        quantity: book.quantity,
        description: book.description,
        author: book.author,
        publisher: book.publisher,
        isbn: book.isbn,
        image: `${environment.API}/${book.image}`,
        category: book.category.id
      }
     
      )
      this.categorySelected =  book.category.id
    })
  }




}

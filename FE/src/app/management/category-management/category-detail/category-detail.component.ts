import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import * as $ from 'jquery';
import { BookDetailComponent } from '../../book-management/book-detail/book-detail.component';
import { BookService } from '../../book-management/shared/services/book.services';
import { CategoryService } from 'src/app/services/category.service';
import { ToastrManager } from 'ng6-toastr-notifications';
import { AbtractComponents } from 'src/app/shared/utils/AbtractComponents';
import { Book, Category } from 'src/app/model/book.model';
import { Subscription, forkJoin } from 'rxjs';
import { environment } from 'src/environments/environment.prod';
@Component({
  selector: 'app-category-detail',
  templateUrl: './category-detail.component.html',
})
export class CategoryDetailComponent extends AbtractComponents implements OnInit {

  form: FormGroup;
  displayedColumns: string[] = ['no', 'name', 'quantity', 'type', 'author'];
  title = 'Thêm'
  // upload file
  selectedFile: File;
  fileName = '';
  view = 1
  edit = true
  info: Category = {
    id: 0,
    name: ''
  }
  books: Book[] = []
  loadCategorySubscription: Subscription
  length = 0
  pageSize = 10
  pageSizeOptions : number[] = [5, 10, 25, 100]
  pageIndex = 0
  constructor(public dialogRef: MatDialogRef<BookDetailComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, 
    private fb: FormBuilder,
    public service: BookService,
    public categoryService: CategoryService,
    public toastr: ToastrManager,
    private bookService: BookService) {
      super(toastr)
  }

  ngOnInit() {
    this.initForm()
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
        this.loadCategoryDetail(id)
        break;
      case 3:
        this.title = 'Chi tiết'
        this.loadCategoryDetail(id)
        break;

      default:
        break;
    }
    this.initForm()
  }

//   /*FORM */
  initForm() {
    this.form = this.fb.group({
      name: ['', [Validators.required]]
    })
  }
  /*ACTION */
  onCancel(data): void {
    this.dialogRef.close(data);
  }

  onSubmitAdd() {
    this.categoryService.addCategory(this.info.name)
      .subscribe(() => {
        this.onCancel({status: 'success'})
        this.notifySucccess('Thêm thể loại mới thành công')
      }, (err) => {
        this.notifyError(err.error ? err.error.message : 'Thêm thể loại không thành công')
      })
  }

  onSubmitEdit() {
    this.categoryService.editCategory(this.info.name, this.data.id)
      .subscribe(() => {
        this.onCancel({status: 'success'})
        this.notifySucccess('Chỉnh sửa thành công')
      }, (err) => {
        this.notifyError(err.error ? err.error.message : 'Chỉnh sửa không thành công')
      })
  }

  loadCategoryDetail(id, pageInfo?: any) {
    if (this.loadCategorySubscription) {
      this.loadCategorySubscription.unsubscribe()
    }
    let data = {
      size: pageInfo ? pageInfo.pageSize : this.pageSize,
      page: pageInfo ? pageInfo.pageIndex : this.pageIndex
    }
    this.loadCategorySubscription = forkJoin(
      this.categoryService.get(id),
      this.bookService.getBooksByCategory(id, data)

    ).subscribe((rs) => {
      this.info = rs[0]
      this.books = rs[1].content
      this.length = rs[1].totalElements
      this.books = this.books.map((book) => {
        book.image = `${environment.API}${book.image}`
        return book
      })
    })
  }

  changePage(event) {
    this.bookService.getBooksByCategory(this.data.id, event)
    .subscribe((books) => {
      this.books = books.content
      this.length = books.totalElements
      this.books = this.books.map((book) => {
        book.image = `${environment.API}${book.image}`
        return book
      })
    })
  }





}

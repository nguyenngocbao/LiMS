import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { CategoryDetailComponent } from '../category-detail/category-detail.component';
import { CategoryService } from 'src/app/services/category.service';
import { ToastrManager } from 'ng6-toastr-notifications';
import { AbtractComponents } from 'src/app/shared/utils/AbtractComponents';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
})
export class CategoryListComponent extends AbtractComponents implements OnInit {


  displayedColumns: string[] = ['stt', 'name', 'number', 'action'];
  dataSource;
  loadSubscription: Subscription

  constructor(
    private categoryService: CategoryService,
    public toastr: ToastrManager, 
    public dialog: MatDialog,
    ) {
      super(toastr)
     }

  ngOnInit() {
    this.getListBook()
  }
  openDialog(view, id): void {
    const dialogRef = this.dialog.open(CategoryDetailComponent, {
      width: '50%', data: {
        view: view,
        id: id
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result && result.status) {
        this.getListBook()
      }
    });
  }

  getListBook() {
    if (this.loadSubscription) {
      this.loadSubscription.unsubscribe()
    }
    this.loadSubscription = this.categoryService.list().subscribe((categories) => {
      this.dataSource = categories
    })
  }

  delele(id) {
    if(confirm('Bạn có chắc chắn muốn xóa thể loại sách và những quyển sách thuộc thể loại này không?')) {
      this.categoryService.delete(id).subscribe(() => {
        this.notifySucccess('Xóa thành công')
        this.getListBook()
      }, (err) => {
        this.notifyError(err.error ? err.error.message : 'Xóa không thành công')
      })
    }
  }

}

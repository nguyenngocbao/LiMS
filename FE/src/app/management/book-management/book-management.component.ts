import { Component, OnInit } from '@angular/core';
import { BookService } from './shared/services/book.services';
import { MatDialog } from '@angular/material';
import { BookDetailComponent } from './book-detail/book-detail.component';
import { viewAttached } from '@angular/core/src/render3/instructions';
import { Subscription } from 'rxjs';
import { environment } from 'src/environments/environment.prod';
import { AbtractComponents } from 'src/app/shared/utils/AbtractComponents';
import { ToastrManager } from 'ng6-toastr-notifications';

@Component({
  selector: 'app-book-management',
  templateUrl: './book-management.component.html',
  styleUrls: ['./book-management.component.css']
})
export class BookManagementComponent extends AbtractComponents implements OnInit {


  displayedColumns: string[] = ['no', 'name', 'image', 'quantity', 'type', 'author', 'action'];
  dataSource;
  loadSubscription: Subscription
  length = 0
  pageSize = 10
  pageSizeOptions : number[] = [5, 10, 25, 100]
  pageIndex = 0
  constructor(public service: BookService, public dialog: MatDialog,
    public toastr: ToastrManager) {
      super(toastr)
     }

  ngOnInit() {
    this.listBook()
  }
  openDialog(view, id): void {
    const dialogRef = this.dialog.open(BookDetailComponent, {
      width: '80%', data: {
        view: view,
        id: id
      }
    });

    dialogRef.afterClosed().subscribe(result => {
        this.listBook()
    });
  }

  listBook(pageInfo?: any) {
    if (this.loadSubscription) {
      this.loadSubscription.unsubscribe()
    }
    let data = {
      size: pageInfo ? pageInfo.pageSize : this.pageSize,
      page: pageInfo ? pageInfo.pageIndex : this.pageIndex
    }
    this.loadSubscription = this.service.getBooks(data)
      .subscribe((books) => {
        this.dataSource = books.content
        this.length = books.totalElements
        this.dataSource = this.dataSource.map((book) => {
          book.image = `${environment.API}${book.image}`
          return book
        })
      })
  }

  delete(id: number) {
    this.service.deleteBook(id).subscribe((_) => {
      this.notifySucccess('Xóa thành công')
      this.listBook()
    }, (err) => {
      this.notifyError(err.error ? err.error.message : 'Xóa không thành công')
    })
  }

  changePage(event) {
    this.listBook(event)
  }

}

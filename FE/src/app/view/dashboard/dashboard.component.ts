import { ToastrService } from 'ngx-toastr';
import { Component, OnInit } from '@angular/core';
import { ViewService } from 'src/app/shared/services/view.service';
import { ConfirmComponent } from 'src/app/shared/components/confirm/confirm.component';
import { MatDialog } from '@angular/material';
import { Subscription } from 'rxjs';
import { environment } from 'src/environments/environment.prod';
import { CategoryService } from 'src/app/services/category.service';
import { Category } from 'src/app/model/book.model';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  constructor(private service: ViewService,public dialog: MatDialog,
    private categoryService: CategoryService) { }
  books = []
  length = 0
  pageSize = 6
  pageSizeOptions : number[] = [6, 12, 24]
  pageIndex = 0
  loadBookSubscription: Subscription
  categories: Category[] = []
  search = ''
  filter = 'name'
  category = '0'
  ngOnInit() {
    this.loadBook({page: 0, size: 6})
    this.loadCategory()
  }
  loadBook(page?: any) {
    if (this.loadBookSubscription) {
      this.loadBookSubscription.unsubscribe()
    }
    let data: any = {
      filter: this.filter,
      category: this.category,
      search: this.search,
    }
    if (page) {
      this.pageIndex = page.page
      this.pageSize = page.size
    }
    data.page = this.pageIndex
    data.size = this.pageSize

    this.loadBookSubscription = this.service.searchBook(data).subscribe(rs => {
      this.books = rs.content
      this.length = rs.totalElements
      this.books = this.books.map((b) => {
        b.image = `${environment.API}/${b.image}`
        return b
      }
      )
    })
  }
  openLoanBook(item){
    const dialogRef = this.dialog.open(ConfirmComponent, {
      width: '500px',
      data: { item:item }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result.status == 'ok') {

      }
    });

  }

  changePage(_) {
    this.loadBook()
  }

  loadCategory() {
    this.categoryService.list().subscribe((categories) => {
      this.categories = categories
    })
  }

  searchBook() {
    this.loadBook({page: 0, size: 6})
  }

  searchBookChanged() {
    this.loadBook({page: 0, size: 6})
  }

}

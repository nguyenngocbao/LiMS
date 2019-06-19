import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { CategoryService } from 'src/app/services/category.service';
import { ToastrManager } from 'ng6-toastr-notifications';
import { AbtractComponents } from 'src/app/shared/utils/AbtractComponents';
import { Subscription } from 'rxjs';
import { RegisterComponent } from 'src/app/register/register.component';
import { UserService } from 'src/app/shared/services/user.service';
import { defaultUrlMatcher } from '@angular/router/src/shared';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
})
export class UserListComponent extends AbtractComponents implements OnInit {


  displayedColumns: string[] = ['stt', 'username', 'email', 'role'];
  dataSource;
  loadSubscription: Subscription
  length = 0
  pageSize = 10
  pageSizeOptions : number[] = [5, 10, 25, 100]
  pageIndex = 0
  constructor(
    private categoryService: CategoryService,
    public toastr: ToastrManager, 
    public dialog: MatDialog,
    private userService: UserService
    ) {
      super(toastr)
     }

  ngOnInit() {
    this.getListUser()
  }
  openDialog(view, id): void {
    const dialogRef = this.dialog.open(RegisterComponent, {
      width: '50%', data: {
        view: view,
        id: id,
        isAdmin: true
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(result)
      if (result && result.status) {
        this.getListUser()
      }
    });
  }

  getListUser(pageInfo?: any) {
    if (this.loadSubscription) {
      this.loadSubscription.unsubscribe()
    }
    let data = {
      size: pageInfo ? pageInfo.pageSize : this.pageSize,
      page: pageInfo ? pageInfo.pageIndex : this.pageIndex
    }
    this.loadSubscription = this.userService.listUser(data).subscribe((users) => {
      this.dataSource = users.content
      this.length = users.totalElements
    })
  }

  displayRole(authorities) {
    let role = authorities && authorities.length > 0  ? authorities[0].authority : ''
      switch(role) {
        case 'ROLE_TEACHER':
          return 'Giáo viên'
        case 'ROLE_ADMIN':
          return 'Quản trị'
        case 'ROLE_USER':
          return 'Người dùng'
        default:
            return 'Người dùng'
      }
  }
  changePage(event) {
    console.log(event)
    this.getListUser(event)
  }

}

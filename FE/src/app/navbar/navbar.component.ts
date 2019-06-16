import { ProfileComponent } from './../profile/profile.component';
import { Component, OnInit, EventEmitter, Output } from '@angular/core'
import { MatDialog } from '@angular/material';
import { LoginComponent } from '../login/login.component';
import { ShareService } from '../services/share.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  @Output()
  openMenu: EventEmitter<boolean> = new EventEmitter<boolean>();
  statusMenu = false
  openProfile = false

  constructor(public dialog: MatDialog, 
    private userService: UserService) { }

  ngOnInit() {
  }
  onOpenMenu() {
    this.statusMenu = !this.statusMenu;
    this.openMenu.emit(this.statusMenu);
  }
  openLogin(): void {
    const dialogRef = this.dialog.open(LoginComponent, {
      width: '500px',
    });

  }
  profile() :void {
    this.openProfile = !this.openProfile
  }

  personal() {
     this.dialog.open(ProfileComponent, {
      width: '500px'
    })
  }

  hasLogin() {
    return localStorage.getItem('token')
  }

  logout() {
    if (confirm('Bạn có muốn đăng xuất không?')) {
      this.userService.logout()
      ShareService.loginEvent.emit('logout')
    }
  }

}

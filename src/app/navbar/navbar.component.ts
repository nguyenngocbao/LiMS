import { Component, OnInit, EventEmitter, Output } from '@angular/core'
import { MatDialog } from '@angular/material';
import { LoginComponent } from '../login/login.component';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  @Output()
  openMenu: EventEmitter<boolean> = new EventEmitter<boolean>();
  statusMenu = false
  
  constructor(public dialog: MatDialog) { }

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

}

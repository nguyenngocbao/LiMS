import { Component, OnInit, EventEmitter, Output } from '@angular/core'

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  @Output()
  openMenu: EventEmitter<boolean> = new EventEmitter<boolean>();
  statusMenu = false
  
  constructor() { }

  ngOnInit() {
  }
  onOpenMenu() {
    this.statusMenu = !this.statusMenu;
    this.openMenu.emit(this.statusMenu);
  }

}

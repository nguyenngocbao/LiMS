import { Component, OnInit } from '@angular/core'
import * as $ from 'jquery';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  ngOnInit(){
    
  }
  title = 'Biostats'
  statusMenu = false;
  openMenu($e){
    console.log($e)
    this.statusMenu = $e
  }
}
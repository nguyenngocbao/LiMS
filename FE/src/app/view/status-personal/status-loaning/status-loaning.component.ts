import { Component, OnInit } from '@angular/core';
import { ViewService } from 'src/app/shared/services/view.service';

@Component({
  selector: 'app-status-loaning',
  templateUrl: './status-loaning.component.html',
  styleUrls: ['./status-loaning.component.css']
})
export class StatusLoaningComponent implements OnInit {
  data;
  status
  constructor(private service: ViewService) {
   }
  ngOnInit() {
    this.loadRequest()
  }
  onDelete(id){
    console.log(id)
    this.delete(id);
  }
  loadRequest() {
    this.service.loadLoaning().subscribe(data => {
      this.data = data
    })
  }
 delete(id){
   this.service.deleteRequest(id).subscribe(data=>{

   },err=>{},()=>{
     this.loadRequest()
   })
 }
 returnBook(id){
  this.service.returnBooks(id).subscribe(data=>{

  },err=>{},()=>{
    this.loadRequest()
  })

 }

}

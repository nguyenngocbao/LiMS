import { Component, OnInit } from '@angular/core';
import { ViewService } from 'src/app/shared/services/view.service';


@Component({
  selector: 'app-status-request-loan',
  templateUrl: './status-request-loan.component.html',
  styleUrls: ['./status-request-loan.component.css']
})
export class StatusRequestLoanComponent implements OnInit {
  data;
  status
  constructor(private service: ViewService) {
   this.status =  {
    WAITING :{text:"Đang chờ phê duyệt",color: '#ffff00'} ,
    LOANING_ACCEPT: {text:"Đã chấp nhận",color: '#00E676'},
    LOANING_REJECT:{text:"Đã từ chối",color: '#DD2C00'}
   }}
  ngOnInit() {
    this.loadRequest()
  }
  onDelete(id){
    console.log(id)
    this.delete(id);
  }
  loadRequest() {
    this.service.loadRequest().subscribe(data => {
      this.data = data
    })
  }
 delete(id){
   this.service.deleteRequest(id).subscribe(data=>{

   },err=>{},()=>{
     this.loadRequest()
   })
 }

}

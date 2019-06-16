import { Component, OnInit } from '@angular/core';
import { ViewService } from 'src/app/shared/services/view.service';
import { MatDialog } from '@angular/material';
import { RejectInfoComponent } from './reject-info/reject-info.component';
import { ConfirmComponent } from 'src/app/shared/components/confirm/confirm.component';
import { ConfirmType } from 'src/app/shared/utils/ConfirmType';


@Component({
  selector: 'app-status-request-loan',
  templateUrl: './status-request-loan.component.html',
  styleUrls: ['./status-request-loan.component.css']
})
export class StatusRequestLoanComponent implements OnInit {
  data;
  status
  constructor(private service: ViewService,public dialog: MatDialog) {
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
  refesh(){
    this.loadRequest()
  }
 delete(id){
   this.service.deleteRequest(id).subscribe(data=>{

   },err=>{},()=>{
     this.loadRequest()
   })
 }
 onViewReason(item){
  const dialogRef = this.dialog.open(RejectInfoComponent, {
    width: '400px',
    data: item
  });

  dialogRef.afterClosed().subscribe(result => {
    this.refesh()
  });

 }
 openConfirm(item) {
  const dialogRef = this.dialog.open(ConfirmComponent, {
    width: '500px',
    data: { item: item.book, type: ConfirmType.CANCEL }
  });

  dialogRef.afterClosed().subscribe(result => {
    if (result.status == 'ok') {
     this.delete(item.id)
    }
  });

}

}

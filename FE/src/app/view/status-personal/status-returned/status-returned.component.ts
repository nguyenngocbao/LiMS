import { Component, OnInit } from '@angular/core';
import { BookService } from 'src/app/management/book-management/shared/services/book.services';
import { ViewService } from 'src/app/shared/services/view.service';

@Component({
  selector: 'app-status-returned',
  templateUrl: './status-returned.component.html',
  styleUrls: ['./status-returned.component.css']
})
export class StatusReturnedComponent implements OnInit {

  displayedColumns: string[] = ['no', 'name', 'quantity', 'type','author','action'];
  dataSource  ;
  length = 0
  pageSize = 10
  pageSizeOptions : number[] = [5, 10, 25, 100]
  pageIndex = 0
status
  constructor(public service: ViewService) {
    this.status =  {
      WAITING :{text:"Đang chờ phê duyệt",color: '#ffff00'} ,
      LOANING_ACCEPT: {text:"Được chấp nhận",color: '#00E676'},
      LOANING_REJECT:{text:"Đã từ chối",color: '#DD2C00'},
      LOANING:{text:"Đang mượn",color: '#DD2C00'},
      RESERVE:{text:"Đng đặt trước",color: '#DD2C00'},
      RETURNING:{text:"Đang trả",color: '#DD2C00'},
      RETURNED:{text:"Đã trả",color: '#DD2C00'},
     }
   }

  ngOnInit() {
    this.load()
    
  }
  load(pageInfo?){
    let data = {
      size: pageInfo ? pageInfo.pageSize : this.pageSize,
      page: pageInfo ? pageInfo.pageIndex : this.pageIndex
    }
    this.service.history(data).subscribe(data =>{
      this.dataSource = data.content
      this.length = data.totalElements
    },err => {

    }, ()=>{

    })
  }
  changePage(event) {
    this.load(event);
  }

}

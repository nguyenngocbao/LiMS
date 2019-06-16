import { Component, OnInit, Inject } from '@angular/core';
import { ViewService } from 'src/app/shared/services/view.service';
import { ConfirmComponent } from 'src/app/shared/components/confirm/confirm.component';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-reject-info',
  templateUrl: './reject-info.component.html',
  styleUrls: ['./reject-info.component.css']
})
export class RejectInfoComponent implements OnInit {

  constructor(public service: ViewService, public dialogRef: MatDialogRef<ConfirmComponent>, @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit() {
  }
  /*ACTION */
  onCancel(): void {
    const data = { status: 'no' }
    this.dialogRef.close(data);
  }
  onSubmit(): void {
   this.service.disable(this.data.id).subscribe(
     data =>{

     },err =>{},()=>{
      const data = { status: 'no' }
      this.dialogRef.close(data);
     }
   )

  }

}

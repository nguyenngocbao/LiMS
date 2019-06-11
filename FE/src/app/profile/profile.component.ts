import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material';
import { HttpErrorResponse } from '@angular/common/http';
import * as $ from 'jquery';
import { MatProgressBarModule } from '@angular/material/progress-bar';
@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
})
export class ProfileComponent implements OnInit {
    imgsrc: string;
    user: any;
    username = '';
    constructor(private dialogRef: MatDialogRef<ProfileComponent>) {
    }
    ngOnInit(): void {
        // $('.openImageUpload').click(function () {
        //     alert('click');
        //     $('#imgupload').trigger('click');
        //     // tslint:disable-next-line:prefer-const
        //     let image: string;
        //     image = '/home/tttthuy/Pictures/hinh.jpg';
        //     console.log($('#imgupload').val());
        //     $('#avatar').attr('src', image);
        // });
    }
    onClose(): void {
        this.dialogRef.close();
    }
    getInfoUser() {
    }
}


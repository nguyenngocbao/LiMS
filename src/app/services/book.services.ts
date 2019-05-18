import { Injectable } from '@angular/core';

@Injectable()
export class BookService {
    BOOKS = [
        {id: 1,name: 'Mắt biếc',image: '', quantity: 5,type: 'Truyện', author: 'Nguyễn Nhật Ánh'},
        {id: 2,name: 'Đảo mộng mơ',image: '', quantity: 5,type: 'Truyện', author: 'Nguyễn Nhật Ánh'},
        {id: 3,name: 'Con chó và con mèo',image: '', quantity: 5,type: 'Truyện', author: 'Nguyễn Nhật Ánh'},
        {id: 4,name: 'Con cá',image: '', quantity: 5,type: 'Truyện', author: 'Nguyễn Nhật Ánh'},
        {id: 5,name: 'Con cá trèo lên cây',image: '', quantity: 5,type: 'Truyện', author: 'Nguyễn Nhật Ánh'}
    ]
    LOANBOOKS = [
        {id: 1,bookName: 'Mắt biếc',UserName: 'nngocbao', requestDate: '25/5/2019'},
        {id: 2,bookName: 'Đảo mộng mơ',UserName: 'nvhoai',requestDate: '25/5/2019'},
        {id: 3,bookName: 'Con chó và con mèo',UserName: 'lthang', requestDate: '25/5/2019'},
        {id: 4,bookName: 'Con cá',UserName: 'ttthuthuy',requestDate: '25/5/2019'},
        {id: 5,bookName: 'Con cá trèo lên cây',UserName: 'ncanh', requestDate: '25/5/2019'}
    ]
    getBook(){
        return this.BOOKS
    }
    getLoanBook(){
        return this.LOANBOOKS
    }
}
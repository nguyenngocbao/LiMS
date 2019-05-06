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
    getBook(){
        return this.BOOKS
    }
}
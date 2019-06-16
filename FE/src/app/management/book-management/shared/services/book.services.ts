import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class BookService {
  
  
 
    public API_URL = environment.API;
    constructor(private http: HttpClient) {
    }
    BOOKS = [
        {id: 1,name: 'Mắt biếc',image: 'https://salt.tikicdn.com/cache/200x200/ts/product/e3/00/44/022f72407f7e6090425b8413358eeaae.jpg', quantity: 5,type: 'Truyện', author: 'Nguyễn Nhật Ánh',description: 'Câu truyện',publisher:'Anpha Book'},
        {id: 2,name: 'Đảo mộng mơ',image: 'https://salt.tikicdn.com/cache/200x200/media/catalog/product/t/u/tuoi-tre-dang-gia-bao-nhieu-u547-d20161012-t113832-888179.u3059.d20170616.t095744.390222.jpg', quantity: 5,type: 'Truyện', author: 'Nguyễn Nhật Ánh',description: 'Câu truyện',publisher:'Anpha Book'},
        {id: 3,name: 'Con chó và con mèo',image: 'https://salt.tikicdn.com/cache/200x200/ts/product/5f/62/71/26b6f3e1029bad38317073706098398c.jpg', quantity: 5,type: 'Truyện', author: 'Nguyễn Nhật Ánh',description: 'Câu truyện',publisher:'Anpha Book'},
        {id: 4,name: 'Con cá',image: 'https://salt.tikicdn.com/cache/200x200/ts/product/c4/46/99/0dcc6e04aca0a78cda09a2d8b156dccb.jpg', quantity: 5,type: 'Truyện', author: 'Nguyễn Nhật Ánh',description: 'Câu truyện',publisher:'Anpha Book'},
        {id: 5,name: 'Con cá trèo lên cây',image: 'https://salt.tikicdn.com/cache/200x200/media/catalog/product/i/m/img555.u2469.d20161124.t092733.970052.jpg', quantity: 5,type: 'Truyện', author: 'Nguyễn Nhật Ánh',description: 'Câu truyện',publisher:'Anpha Book'}
    ]
    LOANBOOKS = [
        {id: 1,bookName: 'Mắt biếc',UserName: 'nngocbao', requestDate: '25/5/2019'},
        {id: 2,bookName: 'Đảo mộng mơ',UserName: 'nvhoai',requestDate: '25/5/2019'},
        {id: 3,bookName: 'Con chó và con mèo',UserName: 'lthang', requestDate: '25/5/2019'},
        {id: 4,bookName: 'Con cá',UserName: 'ttthuthuy',requestDate: '25/5/2019'},
        {id: 5,bookName: 'Con cá trèo lên cây',UserName: 'ncanh', requestDate: '25/5/2019'}
    ]
    NEWBOOK = [
        {id: 1,bookName: 'Mắt biếc',UserName: 'nngocbao', reason: 'bổ sung sách mới',author: 'Nguyễn Nhật Ánh'},
        {id: 2,bookName: 'Đảo mộng mơ',UserName: 'nvhoai',reason: 'bổ sung sách mới',author: 'Nguyễn Nhật Ánh'},
        {id: 3,bookName: 'Con chó và con mèo',UserName: 'lthang', reason: 'bổ sung sách mới',author: 'Nguyễn Nhật Ánh'},
        {id: 4,bookName: 'Con cá',UserName: 'ttthuthuy',reason: 'bổ sung sách mới',author: 'Nguyễn Nhật Ánh'},
        {id: 5,bookName: 'Con cá trèo lên cây',UserName: 'ncanh', reason: 'bổ sung sách mới',author: 'Nguyễn Nhật Ánh'}
    ]
    getBook(){
        return this.BOOKS
    }
    getLoanBook(){
        return this.LOANBOOKS
    }
    loadRequest(): Observable<any>{
        let header = new HttpHeaders();
        header = header.append('Authorization', localStorage.getItem('token'));
        return this.http.get(`${this.API_URL}/api/loan/requestOnly`,
            { headers: header });
    }
    getRequestByUser(id){
        let header = new HttpHeaders();
        header = header.append('Authorization', localStorage.getItem('token'));
        return this.http.get(`${this.API_URL}/api/loan/user/${id}`,
            { headers: header });

    }
    getRequestByBook(id){
        let header = new HttpHeaders();
        header = header.append('Authorization', localStorage.getItem('token'));
        return this.http.get(`${this.API_URL}/api/loan/books/${id}`,
            { headers: header });

    }
    loaningAccept(id: any) {
        let header = new HttpHeaders();
        let body = {id : id}
        header = header.append('Authorization', localStorage.getItem('token'));
        return this.http.post(`${this.API_URL}/api/loan/acceptLoan`,JSON.stringify(body),
            { headers: header });
        
      }
      loaningReject(id: any, reason) {
        let header = new HttpHeaders();
        let body = {id : id,reason : reason}
        header = header.append('Authorization', localStorage.getItem('token'));
        return this.http.post(`${this.API_URL}/api/loan/rejectLoan`,JSON.stringify(body),
            { headers: header });
       
      }
      confirmGetBook(id: any): any {
        let header = new HttpHeaders();
        let body = {id : id}
        header = header.append('Authorization', localStorage.getItem('token'));
        return this.http.post(`${this.API_URL}/api/loan/confiGetBook`,JSON.stringify(body),
            { headers: header });
      }
      loadRequestAccept() {
        let header = new HttpHeaders();
        header = header.append('Authorization', localStorage.getItem('token'));
        return this.http.get(`${this.API_URL}/api/loan/requestAccept`,
            { headers: header });
        
      }
      confirmReturn(id: any): any {
        let header = new HttpHeaders();
        let body = {id : id}
        header = header.append('Authorization', localStorage.getItem('token'));
        return this.http.post(`${this.API_URL}/api/loan/confiReturnBook`,JSON.stringify(body),
            { headers: header });
        
      }
      loadRequestReturning(): any {
        let header = new HttpHeaders();
        header = header.append('Authorization',localStorage.getItem('token'));
        return this.http.get(`${this.API_URL}/api/loan/requestReturning`,
            { headers: header });
      }
      
      
}
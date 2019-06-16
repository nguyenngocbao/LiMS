import { Observable, of } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment.prod';
import { Page } from 'src/app/model/page.model';
import { Book } from 'src/app/model/book.model';
@Injectable()
export class BookService {
    public API_URL = `${environment.API}/api/book`;
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

    getBooks(data): Observable<Page>{
        let httpParams = new HttpParams()
        httpParams.append('size', data.size || 10)
        httpParams.append('page', data.page || 0)
        return this.http.get<Page>(this.API_URL, {params: httpParams})
    }

    addBook(data, file, id): Observable<Book> {
        let body = new FormData();
        delete data.category
        body.append('data', JSON.stringify(data));
        body.append('file', file);
        return this.http.post<Book>(`${this.API_URL}/category/${id}`, body)
    }

    editBook(data, file, id): Observable<Book> {
        if (file) {
            let body = new FormData();
            body.append('data', JSON.stringify(data));
            body.append('file', file);
            return this.http.post<Book>(`${this.API_URL}/file/${id}`, body)
        } else {
            return this.http.put<Book>(`${this.API_URL}/${id}`, data)
        }
    }

    getBooksByCategory(categoryId, data): Observable<Page> {
        let httpParams = new HttpParams()
        httpParams.append('size', data.size || 10)
        httpParams.append('page', data.page || 0)
        return this.http.get<Page>(`${this.API_URL}/category/${categoryId}`, {params: httpParams})
    }

    getBook() {
        return this.BOOKS
    }
    getLoanBook(){
        return this.LOANBOOKS
    }

    getBookById(id: number) {
        return this.http.get<Book>(`${this.API_URL}/${id}`)
    }

    deleteBook(id: number) {
        return this.http.delete(`${this.API_URL}/${id}`)
    }
}
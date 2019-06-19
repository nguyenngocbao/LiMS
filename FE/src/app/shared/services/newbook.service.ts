import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { NewBook } from '../models/newBook';
import { environment } from 'src/environments/environment';

@Injectable({ providedIn: 'root' })
export class NewBookService {

    public API_URL = environment.API;
    constructor(private http: HttpClient) { }
    requestNewBook(book: NewBook): Observable<any> {

        let body: FormData = new FormData();
        body.append('author', book.author)
        body.append('reason', book.reason)
        body.append('bookname', book.bookName)
        let header = new HttpHeaders();

        return this.http.post(`${this.API_URL}/api/request`, body,
            { headers: header });
    }
    loadRequest(): any {
        let header = new HttpHeaders();
        return this.http.get(`${this.API_URL}/api/request`,
            { headers: header });
    }

}
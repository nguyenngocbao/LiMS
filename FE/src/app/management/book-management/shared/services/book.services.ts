import { Observable, of } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment.prod';
import { Page } from 'src/app/model/page.model';
import { Book } from 'src/app/model/book.model';
@Injectable()
export class BookService {
    public API_URL = `${environment.API}/api/book`;
    constructor(private http: HttpClient) {
    }
   
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

      
      

    getBookById(id: number) {
        return this.http.get<Book>(`${this.API_URL}/${id}`)
    }

    deleteBook(id: number) {
        return this.http.delete(`${this.API_URL}/${id}`)
    }
}
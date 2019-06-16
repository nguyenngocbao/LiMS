import { Observable, of } from 'rxjs';
import { environment } from '../../environments/environment.prod';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Category } from '../model/book.model';
@Injectable()
export class CategoryService {
    public API_URL = `${environment.API}/api/category`;
    constructor(private http: HttpClient) {
    }

    public addCategory(name: string): Observable<any> {
        return this.http.post(this.API_URL, name)
    }

    public editCategory(name: string, id: number): Observable<any> {
        return this.http.put(`${this.API_URL}/${id}`, name)
    }

    list(): Observable<any> {
        return this.http.get(this.API_URL)
    }

    delete(id): Observable<any> {
        return this.http.delete(`${this.API_URL}/${id}`)
    }

    get(id): Observable<Category> {
        return this.http.get<Category>(`${this.API_URL}/${id}`)
    }

}

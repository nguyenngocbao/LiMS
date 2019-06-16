import { Observable, of } from 'rxjs';

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
@Injectable()
export class UserService {
    public API_URL = environment.API;
    constructor(private http: HttpClient) {
    }

    public login(username: string, password: string): Observable<any> {
        // tslint:disable-next-line:prefer-const
        let body = new FormData();
        body.append('username', username);
        body.append('password', password);
        return this.http.post(`${this.API_URL}/login`, body);
    }

    public register(data, file): Observable<any> {
        // tslint:disable-next-line:prefer-const
        let body = new FormData();
        body.append('data', JSON.stringify(data));
        body.append('file', file);
        return this.http.post(`${this.API_URL}/api/user/create`, body);
    }


    public logout() {
        localStorage.removeItem('token');
        window.location.reload();
    }

    public isAdmin(): Observable<boolean> {
        if (! localStorage.getItem('token')) {
            return of(false)
        }
        return this.http.get<boolean>(`${this.API_URL}/api/user/isAdmin`)
    }


}

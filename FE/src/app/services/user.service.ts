import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.prod';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
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

    public logout() {
        localStorage.removeItem('token');
        window.location.reload();
    }


}
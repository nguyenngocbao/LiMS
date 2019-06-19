import { Observable, of } from 'rxjs';

import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Page } from 'src/app/model/page.model';
import { registerNgModuleType } from '@angular/core/src/linker/ng_module_factory_loader';
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

    public registerByAdmin(data): Observable<any> {
        // tslint:disable-next-line:prefer-const
        // let body = new FormData();
        // body.append('data', JSON.stringify(data));
        // body.append('file', file);
        return this.http.post(`${this.API_URL}/api/user/admin/create`, data);
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

    public listUser(data): Observable<Page> {
        let httpParams = new HttpParams()
        .set('size', data.size || 10)
        .set('page', data.page || 0)
        return this.http.get<Page>(`${this.API_URL}/api/user/list`, {params: httpParams})
    }

    public changePassword(data): Observable<any> {
        return this.http.put(`${this.API_URL}/api/user/change-password`, data)
    }

    public forgetPassword(data): Observable<any> {
        return this.http.put(`${this.API_URL}/api/user/forget-password`, data)
    }

}

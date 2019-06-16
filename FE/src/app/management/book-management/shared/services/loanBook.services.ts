import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({providedIn: 'root'})
export class LoanBookService {
    public API_URL = `${environment.API}`;
    constructor(private http: HttpClient) { }
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
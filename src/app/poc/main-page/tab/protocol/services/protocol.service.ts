import { Injectable } from '@angular/core'
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { environment } from 'src/environments/environment'
import { Observable } from 'rxjs'

@Injectable()
export class ProtocolService {
    public API_URL = environment.API
    constructor(private http: HttpClient) {

    }
    loadContent(name: any, index): Observable<any> {
        const  header = new HttpHeaders()
        return this.http.get(this.API_URL + `/api/protocolcontent/${name}/${index}`,
          { headers: header, responseType: 'text' })
      }
      loadTree(name: any): Observable<any> {
        const header = new HttpHeaders()
          return this.http.get(this.API_URL + `/api/protocolsection/${name}`,
            { headers: header })
        }
        uploadFile(file: File): Observable<any> {
          const uploadData = new FormData();
          uploadData.append('myFile', file, file.name);
          return this.http.post(this.API_URL + '/api/protocolcontent/Post', uploadData, {
            observe: 'events',
            headers: new HttpHeaders({
            })
          });
        }

}

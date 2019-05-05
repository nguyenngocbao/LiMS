import { Pipe, PipeTransform } from '@angular/core';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser'

@Pipe({
   name: 'safeHtml',
   pure: false
})
export class SafeHtmlPipe implements PipeTransform 
{
    constructor(private sanitized: DomSanitizer) {}

    transform(value: string, arr:any): SafeHtml{
       return this.sanitized.bypassSecurityTrustHtml(value);
    }
}

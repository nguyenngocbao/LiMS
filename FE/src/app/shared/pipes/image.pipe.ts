import {Pipe, PipeTransform } from '@angular/core'
import { environment } from 'src/environments/environment.prod';

@Pipe({
    name: 'image',
    pure: false
})
export class ImagePipe implements PipeTransform {
    transform(value: any, ...args: any[]) {
        return environment.API+ `/${value}`;
    }
}
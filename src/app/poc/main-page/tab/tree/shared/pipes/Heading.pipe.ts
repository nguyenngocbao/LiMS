import { Pipe, PipeTransform } from '@angular/core'

@Pipe({
 name: 'headingPoc'
})
export class HeadingPocPipe implements PipeTransform {
  transform(value: any, ...args: any[]) {
    const re = /([\d|.]+)(.+)(PAGEREF.*)/
    const S1 = /([\d|.]*\d$)/
    let a = value
    if(a.match(re)){
      let s1 = a.replace(re, `$1`)
          s1 = s1.replace(S1, `$&.`)
        const s2 = a.replace(re, `$2`)
      return `${s1}${s2}`

    }
    return a;
   
  }
}

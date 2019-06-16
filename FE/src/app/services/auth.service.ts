import { Injectable } from "@angular/core";
import { CanActivate } from '@angular/router/src/utils/preactivation';

import { Observable, of } from 'rxjs';
import { MatDialog } from '@angular/material';
import { LoginComponent } from '../login/login.component';
import { Router } from '@angular/router';
import { mergeMap } from 'rxjs/operators';
import { UserService } from '../shared/services/user.service';

@Injectable()
export class AuthService implements CanActivate {
  path: import("@angular/router").ActivatedRouteSnapshot[];
  route: import("@angular/router").ActivatedRouteSnapshot;
  constructor(private userService: UserService, public dialog: MatDialog, private router: Router) {}
 
  canActivate(): Observable<boolean>{
    return this.userService.isAdmin().pipe(mergeMap((isadmin) => {
      if (isadmin) {
        return of(true)
      } else {
        this.router.navigate([''])
        this.openLogin()
        return of(false)
      }
    }))
  }

  openLogin(): void {
    this.dialog.open(LoginComponent, {
      width: '500px',
    });
}
}
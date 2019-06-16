import { ProfileComponent } from './profile/profile.component';
import { BrowserModule, DomSanitizer } from '@angular/platform-browser'
import { NgModule } from '@angular/core'
import { AppRoutingModule } from './app-routing.module'
import { AppComponent } from './app.component'
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http'
import { MatIconRegistry } from '@angular/material'
import { MaterialModule } from './shared/material.module'
import { NavbarComponent } from './navbar/navbar.component';
import { SliderLeftComponent } from './slider-left/slider-left.component';
import * as $ from 'jquery';
import { LoginComponent } from './login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UserService } from './services/user.service';
import { ToastrModule, ToastrManager } from 'ng6-toastr-notifications';
import { AuthInterceptor } from './services/auth.interceptor.service';
import { AuthService } from './services/auth.service';
import { ShareService } from './services/share.service';
@NgModule({
  declarations: [
    AppComponent,NavbarComponent, SliderLeftComponent, LoginComponent, ProfileComponent
  ],
  imports: [
    BrowserModule,  BrowserAnimationsModule,
    AppRoutingModule, HttpClientModule , MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    ToastrModule.forRoot(
      )
  ],
  providers: [UserService, AuthService, ShareService,ToastrManager, {
    provide : HTTP_INTERCEPTORS,
    useClass: AuthInterceptor,
    multi   : true,
  }],
  entryComponents:[LoginComponent, ProfileComponent],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(matIconRegistry: MatIconRegistry, domSanitizer: DomSanitizer) {
    matIconRegistry.addSvgIconSet(domSanitizer.bypassSecurityTrustResourceUrl('./assets/mdi.svg'))
  }
 }

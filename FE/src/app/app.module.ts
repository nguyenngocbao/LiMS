import { ProfileComponent } from './profile/profile.component';
import { BrowserModule, DomSanitizer } from '@angular/platform-browser'
import { NgModule } from '@angular/core'
import { AppRoutingModule } from './app-routing.module'
import { AppComponent } from './app.component'
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http'
import { MatIconRegistry } from '@angular/material'
import { MaterialModule } from './shared/material.module'
import { NavbarComponent } from './navbar/navbar.component';
import { SliderLeftComponent } from './slider-left/slider-left.component';
import * as $ from 'jquery';
import { LoginComponent } from './login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ToastrModule, ToastrManager } from 'ng6-toastr-notifications';
import { UserService } from './shared/services/user.service';
import { ConfirmComponent } from './shared/components/confirm/confirm.component';
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
  providers: [UserService,ToastrManager],
  entryComponents:[LoginComponent, ProfileComponent],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(matIconRegistry: MatIconRegistry, domSanitizer: DomSanitizer) {
    matIconRegistry.addSvgIconSet(domSanitizer.bypassSecurityTrustResourceUrl('./assets/mdi.svg'))
  }
 }

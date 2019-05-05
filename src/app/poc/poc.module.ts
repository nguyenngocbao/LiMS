import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DomSanitizer } from '@angular/platform-browser';
import { PocRoutingModule } from './poc-routing.module';

import { MainPageComponent } from './main-page/main-page.component';
import { EditorModule } from '@tinymce/tinymce-angular';


import { FormsModule, ReactiveFormsModule } from '@angular/forms';
// Flex layout
import { FlexLayoutModule } from '@angular/flex-layout'
import { TabComponent } from './main-page/tab/tab.component'
import { ProtocolComponent } from './main-page/tab/protocol/protocol.component'
import { MaterialModule } from '../shared/material.module'
import { TreeComponent } from './main-page/tab/tree/tree.component'
import { NavbarComponent } from './main-page/navbar/navbar.component'
import { HeadingPocPipe } from './main-page/tab/tree/shared/pipes/Heading.pipe'
import { ProtocolService } from './main-page/tab/protocol/services/protocol.service'
import { SafeHtmlPipe } from './shared/pipes/safeHtml.pipe'
import { SapComponent } from './main-page/tab/sap/sap.component'

import { SapService } from './main-page/tab/sap/shared/services/sap.services';
import { EditorComponent } from './editor/editor.component';
import { SpecComponent } from './main-page/tab/spec/spec.component';
import { SpecService } from './main-page/tab/spec/shared/services/spec.services';
import { MatIconRegistry } from '@angular/material';
import { CommonComponent } from './main-page/tab/common/common.component';
import { CommonService } from './main-page/tab/common/shared/services/common.services';
import { UploadComponent } from './main-page/tab/protocol/upload/upload.component';

@NgModule({
  declarations: [NavbarComponent, MainPageComponent, TabComponent,
    ProtocolComponent, TreeComponent, HeadingPocPipe, SafeHtmlPipe,
    SapComponent, EditorComponent, SpecComponent, CommonComponent, UploadComponent],
  imports: [
    CommonModule,
    PocRoutingModule,
    MaterialModule,
    EditorModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule
  ],
  providers: [ProtocolService, SapService, SpecService,CommonService],
  entryComponents: [UploadComponent]
})
export class PocModule {
  constructor(matIconRegistry: MatIconRegistry, domSanitizer: DomSanitizer) {
    matIconRegistry.addSvgIconSet(domSanitizer.bypassSecurityTrustResourceUrl('./assets/mdi.svg'))
  }
}

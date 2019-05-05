import { Component, OnInit, Output, EventEmitter, Input, ViewChild } from '@angular/core'
import { SapService } from './shared/services/sap.services'
import { sapConfig } from '../../../editor/shared/editor.config'
import { EditorComponent } from '../../../editor/editor.component'

@Component({
  selector: 'app-sap',
  templateUrl: './sap.component.html',
  styleUrls: ['./sap.component.css']
})
export class SapComponent implements OnInit {
  @ViewChild(EditorComponent)
     private editor: EditorComponent
  @Input()
  loadEditor: boolean
  @Output()
  processbar: EventEmitter<boolean> = new EventEmitter<boolean>()
  @Output()
  forwardProtocol: EventEmitter<string> = new EventEmitter<string>()

  projectName = 'Alpha_Test_001';
  contentNode = '';
  imageUrl = 'http://192.168.191.225:9434/api/docx/images';
  dataTree = [];
  fileName = '';
  // upload file
  selectedFile: File;
  tinyConfig = sapConfig;
 
  editable = true;
  constructor(public sapService: SapService) { }

  ngOnInit() {
    this.loadTree(this.projectName)
  }

  viewContent(event) {
    this.getContent(this.projectName, event)
  }
  loadTree(name) {
    this.sapService.loadTree(name).subscribe((data) => {
      this.dataTree = data.Children
    }, err => {

    }
    )
  }
  getContent(name, id) {
    this.processbar.emit(true)
    this.sapService.loadContent(name, id).subscribe((data) => {
      this.contentNode = data;

    }, err => {
    },
    () => {
     this.processbar.emit(false)
    })
  }
  onSapSave($event) {
    this.editor.saveContent('Sap Save')
  }
  onFileSelected(event) {
    this.selectedFile = <File>event.target.files[0];
    this.fileName = this.selectedFile.name.split('.')[0];
    this.uploadFile();

  }
  uploadFile(){
    this.processbar.emit(true);
    this.sapService.uploadFile(this.selectedFile).subscribe((res) => {

    }, err => {
      console.log(err.error);

    }, () => {
      this.processbar.emit(false);
    }
    );

  }

  //link protocol
  linkProtocol(event){
    this.forwardProtocol.emit(event)
    console.log(event)
    // console.log(this.forwardProtocol)
    //this.loadEditor = false

  }

}

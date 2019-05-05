import { Component, OnInit, Output, Input, EventEmitter } from '@angular/core';
import { SpecService } from './shared/services/spec.services';
import { sapConfig } from 'src/app/poc/editor/shared/editor.config';

@Component({
  selector: 'app-spec',
  templateUrl: './spec.component.html',
  styleUrls: ['./spec.component.css']
})
export class SpecComponent implements OnInit {

  @Input()
  loadEditor: boolean ;
  @Output()
  processbar: EventEmitter<boolean> = new EventEmitter<boolean>()

  projectName = 'Alpha_Test_001';
  contentNode = '';
  imageUrl = 'http://192.168.191.225:9434/api/docx/images';
  dataTree = [];
  fileName = '';
  // upload file
  selectedFile: File;
  tinyConfig = sapConfig;
 
  editable = true;
  constructor(public service: SpecService) { }

  ngOnInit() {
    this.loadTree(this.projectName)
  }

  viewContent(event) {
    this.getContent(this.projectName, event)
  }
  loadTree(name){
    this.service.loadTree(name).subscribe((data) => {
      console.log(data)
      this.dataTree = data.Children
    }, err => {

    }
    )
  }
  getContent(name, id) {
    this.processbar.emit(true)
    this.service.loadContent(name, id).subscribe((data) => {
      this.contentNode = data;

    }, err => {
    },
    () => {
     this.processbar.emit(false);
    })
  }
  onFileSelected(event) {
    this.selectedFile = <File>event.target.files[0];
    this.fileName = this.selectedFile.name.split('.')[0];
    this.uploadFile();

  }
  uploadFile(){
    this.processbar.emit(true);
    this.service.uploadFile(this.selectedFile).subscribe((res) => {

    }, err => {
      console.log(err.error);

    }, () => {
      this.processbar.emit(false);
    }
    );

  }

}

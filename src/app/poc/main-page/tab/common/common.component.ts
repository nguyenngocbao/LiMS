import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { sapConfig } from 'src/app/poc/editor/shared/editor.config';

import { CommonService } from './shared/services/common.services';

@Component({
  selector: 'app-common',
  templateUrl: './common.component.html',
  styleUrls: ['./common.component.css']
})
export class CommonComponent implements OnInit {

  @Input()
  loadEditor: boolean ;
  @Output()
  processbar: EventEmitter<boolean> = new EventEmitter<boolean>()

  projectName = 'Alpha_Test_001';
  contentNode = '';
  dataTree = [];
  fileName = '';
  // upload file
  selectedFile: File;
  tinyConfig = sapConfig;
 
  editable = true;
  // width of element
  
  constructor(public service: CommonService) { }

  ngOnInit() {
    this.loadTree(this.projectName)
  }

  viewContent(event) {
    this.getContent(this.projectName, event)
  }
  loadTree(name){
    this.service.loadTree(name).subscribe((data) => {
      this.dataTree = this.convert(data)
      //this.dataTree = data.Children
    }, err => {

    }
    )
  }
 
  convert(data){
   const DATA = [
    
    ]
    //data.Text = 'Spec and Sap'
    data.Children = data.Children.map(data =>{
      data.Text = this.replaceHeading(data.Text)
      return data

    })
    DATA.push(data)
    return DATA ;

    
  }
  replaceHeading(input:string):string{
    const re = /([\d|.]+)(.+)(PAGEREF.*)/
    return input.replace(re,'$2')

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
  removeNumberheading(input){
    const re = /(<span[\s|(lang="x\-none")]+class="pt-Hyperlink[\-\d]*">)([\d|.]+)(<\/span>)/g
    return input.replace(re,'$1$3')
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

    }, () => {
      this.processbar.emit(false);
    }
    );

  }
}

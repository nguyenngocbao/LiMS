import { Component, OnInit, EventEmitter, Output, AfterViewInit, Input, ViewChild } from '@angular/core'
import { ProtocolService } from './services/protocol.service'
import { protocolConfig } from '../../../editor/shared/editor.config'
import { EditorComponent } from '../../../editor/editor.component'
import { TreeComponent } from '../tree/tree.component'
import { MatDialog } from '@angular/material';
import { UploadComponent } from './upload/upload.component';

@Component({
  selector: 'app-protocol',
  templateUrl: './protocol.component.html',
  styleUrls: ['./protocol.component.css'],
})
export class ProtocolComponent implements OnInit {
  @ViewChild(EditorComponent)
  private editor: EditorComponent
  @ViewChild(TreeComponent)
  private tree: TreeComponent
  @Input()
  loadEditor: boolean;
  @Input()
  linkProtocol: string;
  @Output()
  //status of View
  statusView: Status;
  //
  a = 'sadadafsafsa'
  processbar: EventEmitter<boolean> = new EventEmitter<boolean>();
  contentFile = '';
  projectName = 'Alpha_Test_001';
  version = '1.0'

  tinyConfig = protocolConfig;
  dataTree = [];
  editable = false;
  //wigth of element
  wigthEle: string[] = ['24%', '75.99%','20%']
  constructor(public protocolService: ProtocolService, public dialog: MatDialog) {
    this.statusView = Status.Megre
  }

  ngOnInit() {
    this.loadTree(this.projectName);
  }
  viewContent(event) {
    switch (this.statusView) {
      case Status.Megre:
        this.wigthEle = ['24%', '55.99%', '20%']
        this.getContentMegre(this.projectName, event)

        break;

      default:
        this.getContent(this.projectName, event);
        break;
    }

  }
  loadTree(name) {
    this.protocolService.loadTree(name).subscribe((data) => {
      this.dataTree = data.Children;
    }, err => {
      // TODO:
    })
  }
  //get content 
  getContent(name, id) {
    this.processbar.emit(true)
    this.protocolService.loadContent(name, id).subscribe((data) => {
      this.contentFile = data;
    }, err => {
      // TODO:
    },
      () => {
        this.processbar.emit(false);
      });
  }
  //get content when megre code
  getContentMegre(name, id) {
    this.contentFile = 'The severity <del>of the AE will be characterized as mild, moderate, or</del> <ins>laws</ins> severe, to the following definitions: the safety analyses.The severity of the AE will be characterized as mild, moderate, or severe, to the following definitions: the safety analyses.The severity laws severe, to the following definitions: the safety analyses.'
  }
  //*upload file */
  openDialog(): void {
    const dialogRef = this.dialog.open(UploadComponent, {
      width: '400px',
      data: { name: this.projectName, version: this.version }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result.status == 'ok') {
        this.statusView = Status.Megre

      }
    });
  }

  onSave($event) {
    this.editor.saveContent('Protocol Save')
  }
  viewId(datas) {
    this.tree.viewContent(parseInt(datas[1], 0))
    this.processbar.emit(true)
    const myInterval = setInterval(() => {
      if (!(typeof this.editor === 'undefined') && this.editor.isInitSuccess) {
        this.editor.selectComponentByID(datas[2])
        clearInterval(myInterval)
        this.processbar.emit(false)
      }
    }, 200)
  }

}
//Enum status
enum Status {
  Nomal = 1,
  Megre = 2
}
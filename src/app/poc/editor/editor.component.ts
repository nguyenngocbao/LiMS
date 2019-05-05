import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core'
import { HighLightor } from './module/editor.highlight'
import { ChangeManager } from './module/editor.change-manager'
import { TabService } from '../main-page/tab/shared/tab.service'
@Component({
  selector: 'app-editor',
  templateUrl: './editor.component.html',
  styleUrls: ['./editor.component.css']
})
export class EditorComponent implements OnInit {
  @Output()
  dataChange = new EventEmitter<string>()
  @Input() data: string
  get getData() {
    return this.data;
  }
  set setData(val: string) {
    this.data = val;
    this.dataChange.emit(this.data);
  }
  @Input() tinyConfig: object;
  @Input() editable: boolean;

  config: object
  dataModel: string
  highLightor: any
  changManager: any
  editor: any
  isContentLoaded = false
  isInitSuccess = false
  constructor(private tabService: TabService) { }
  ngOnInit() {
    this.config = this.tinyConfig;
    this.dataModel = this.data;
  }
  onInit($event: any) {
    this.editor = $event.editor
    $event.editor.getBody().setAttribute('contenteditable', this.editable)
    this.highLightor = new HighLightor(this.editor.dom.select('[highlight]'))
    this.highLightor.highLightAndBindAction(this.editor.dom, 'dblclick', this.gotoSectionLink, this)
    if (this.editable) {
      this.changManager = new ChangeManager(this.getTrackElements())
    }
    this.isInitSuccess = true
    setTimeout(() => { this.tabService.disableProcessbar() }, 100)
  }
  onBeforeSetContent($event) {
    // this.tabService.disableAllTabs()
  }
  onSetContent($event: any) {
    this.highLightor.elements = this.editor.dom.select('[highlight]')
    this.highLightor.highLightAndBindAction(this.editor.dom, 'dblclick', this.gotoSectionLink, this)
    if (this.editable) {
      this.changManager.trackNodes = this.getTrackElements()
    }
    this.isContentLoaded = true
    this.scrollToTop()
  }
  onNodeChange($event: any) {
    if (this.editable && this.isContentLoaded) {
      this.changManager.watchNodeChange($event.event.parents)
    }
  }
  getTrackElements() {
    const result = this.editor.dom.select('[itemid]')
    result.forEach((el) => {
      el.id = el.getAttribute('itemid')
    })
    return result
  }
  gotoSectionLink($event) {
    // const link = $event.currentTarget.getAttribute('maplink')
    // for demo only
    const link = '0_4_25'
    if (link) {
      this.tabService.goto(link)
      this.editor.fire('blur')
    }
  }
  selectComponentByID(id: any) {
    // const element = this.editor.dom.select(`[itemid=${id}]`)
    // for demo only
    const element = this.editor.dom.select('p')[parseInt(id, 0)]
    this.scrollToTargetElement(element, true)
  }
  scrollToTargetElement(el: any, select: boolean) {
    el.scrollIntoView()
    if (select) {
      this.editor.selection.select(el)
    }
  }
  scrollToTop() {
    this.editor.dom.doc.body.scrollTop = 0
    this.editor.dom.doc.documentElement.scrollTop = 0
  }
  saveContent(val: any) {
    if (this.editor.isDirty()) {
      console.log('save')
      console.log('New Element', this.changManager.getNewElements())
      console.log('Deleted Element', this.changManager.getDeletedElements())
      console.log('Modified Element', this.changManager.getModifiedElements())
      console.log('Result', this.changManager.getResult())
    } else {
      console.log('Nochange need to save')
    }

  }
}

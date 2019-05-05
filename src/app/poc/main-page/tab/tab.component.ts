import { Component, OnInit, OnDestroy, ViewEncapsulation, ViewChild } from '@angular/core'
import { MatTabChangeEvent } from '@angular/material'
import { TabService } from './shared/tab.service'
import { ProtocolComponent } from './protocol/protocol.component'
@Component({
  selector: 'app-tab',
  templateUrl: './tab.component.html',
  styleUrls: ['./tab.component.css'],
  //encapsulation: ViewEncapsulation.None
})
export class TabComponent implements OnInit, OnDestroy {
  @ViewChild(ProtocolComponent)
     private protocol: ProtocolComponent
  isDisable = [false, false, false , false]
  tabIndex = 0
  tabOpen = 0
  processBar = false
  constructor(private tabService: TabService) { }
  ngOnInit() {
    this.tabService.forwardLink.subscribe((data) => {
      this.gotoSection(data)
    })
    this.tabService.controlTab.subscribe((status) => {
      switch(status) {
        case 0:
        this.disableAllTab()
        break
        case 1: 
        this.enableAllTab()
        break
        default:
        break
      }
    })
    this.tabService.controlProcessbar.subscribe((data) => {
      if(data) {
        this.processBar = true
      }else {
        this.processBar = false
      }
    })
  }
  ngOnDestroy() {
    this.tabService.forwardLink.unsubscribe()
    this.tabService.controlTab.unsubscribe()
    this.tabService.controlProcessbar.unsubscribe()
  }
  onProcessbar(event) {
    this.processBar = event
  }
  tabSwitch(tabEvent: MatTabChangeEvent) {
    this.tabOpen = tabEvent.index
    this.processBar = true
  }
  gotoSection(data: string) {
    const datas = data.split('_')
    this.tabOpen = parseInt(datas[0], 0)
    this.protocol.viewId(datas)
  }
  updateEditor($event) {
    this.tabIndex = this.tabOpen
  }
  disableAllTab() {
    this.isDisable = [true, true, true , true]
  }
  enableAllTab() {
    this.isDisable = [false, false, false , false]
  }
  
}

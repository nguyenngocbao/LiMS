import { Injectable, Output, EventEmitter } from '@angular/core'

@Injectable({ providedIn: 'root'})
export class TabService {
  @Output() forwardLink: EventEmitter<string> = new EventEmitter()
  @Output() controlTab: EventEmitter<number> = new EventEmitter()
  @Output() controlProcessbar: EventEmitter<number> = new EventEmitter()
  constructor() { }
  goto(link: string) {
    this.forwardLink.emit(link)
  }
  disableAllTabs() {
    this.controlTab.emit(0)
  }
  enableAllTabs() {
    this.controlTab.emit(1)
  }
  disableProcessbar() {
    this.controlProcessbar.emit(0)
  }
  enableProcessbar() {
    this.controlProcessbar.emit(1)
  }
}

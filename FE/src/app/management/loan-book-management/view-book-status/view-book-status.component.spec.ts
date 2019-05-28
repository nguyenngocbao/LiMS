import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewBookStatusComponent } from './view-book-status.component';

describe('ViewBookStatusComponent', () => {
  let component: ViewBookStatusComponent;
  let fixture: ComponentFixture<ViewBookStatusComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewBookStatusComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewBookStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

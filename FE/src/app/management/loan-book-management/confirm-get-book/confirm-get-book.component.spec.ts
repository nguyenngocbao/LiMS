import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmGetBookComponent } from './confirm-get-book.component';

describe('ConfirmGetBookComponent', () => {
  let component: ConfirmGetBookComponent;
  let fixture: ComponentFixture<ConfirmGetBookComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConfirmGetBookComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfirmGetBookComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

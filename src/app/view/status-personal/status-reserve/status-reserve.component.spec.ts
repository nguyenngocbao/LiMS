import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StatusReserveComponent } from './status-reserve.component';

describe('StatusReserveComponent', () => {
  let component: StatusReserveComponent;
  let fixture: ComponentFixture<StatusReserveComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StatusReserveComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StatusReserveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

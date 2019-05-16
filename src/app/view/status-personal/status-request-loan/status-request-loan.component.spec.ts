import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StatusRequestLoanComponent } from './status-request-loan.component';

describe('StatusRequestLoanComponent', () => {
  let component: StatusRequestLoanComponent;
  let fixture: ComponentFixture<StatusRequestLoanComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StatusRequestLoanComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StatusRequestLoanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

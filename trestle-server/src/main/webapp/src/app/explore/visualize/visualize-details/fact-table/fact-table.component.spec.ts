import {ComponentFixture, TestBed} from '@angular/core/testing';

import {FactTableComponent} from './fact-table.component';

describe('FactTableComponent', () => {
  let component: FactTableComponent;
  let fixture: ComponentFixture<FactTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FactTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FactTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

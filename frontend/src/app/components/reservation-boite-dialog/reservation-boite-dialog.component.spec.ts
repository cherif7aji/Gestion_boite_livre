import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservationBoiteDialogComponent } from './reservation-boite-dialog.component';

describe('ReservationBoiteDialogComponent', () => {
  let component: ReservationBoiteDialogComponent;
  let fixture: ComponentFixture<ReservationBoiteDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReservationBoiteDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ReservationBoiteDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

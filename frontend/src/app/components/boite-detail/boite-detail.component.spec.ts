import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoiteDetailComponent } from './boite-detail.component';

describe('BoiteDetailComponent', () => {
  let component: BoiteDetailComponent;
  let fixture: ComponentFixture<BoiteDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BoiteDetailComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BoiteDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

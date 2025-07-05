import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoiteEditComponent } from './boite-edit.component';

describe('BoiteEditComponent', () => {
  let component: BoiteEditComponent;
  let fixture: ComponentFixture<BoiteEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BoiteEditComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BoiteEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

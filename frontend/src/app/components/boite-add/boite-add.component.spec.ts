import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoiteAddComponent } from './boite-add.component';

describe('BoiteAddComponent', () => {
  let component: BoiteAddComponent;
  let fixture: ComponentFixture<BoiteAddComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BoiteAddComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BoiteAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoiteListComponent } from './boite-list.component';

describe('BoiteListComponent', () => {
  let component: BoiteListComponent;
  let fixture: ComponentFixture<BoiteListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BoiteListComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BoiteListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SlicedSectionComponent } from './sliced-section.component';

describe('SlicedSectionComponent', () => {
  let component: SlicedSectionComponent;
  let fixture: ComponentFixture<SlicedSectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SlicedSectionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SlicedSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

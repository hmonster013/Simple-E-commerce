import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EcommerceCommonComponent } from './ecommerce-common.component';

describe('EcommerceCommonComponent', () => {
  let component: EcommerceCommonComponent;
  let fixture: ComponentFixture<EcommerceCommonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EcommerceCommonComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EcommerceCommonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { TestBed } from '@angular/core/testing';

import { EcommerceCommonService } from './ecommerce-common.service';

describe('EcommerceCommonService', () => {
  let service: EcommerceCommonService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EcommerceCommonService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

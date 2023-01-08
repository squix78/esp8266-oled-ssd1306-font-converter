import { TestBed } from '@angular/core/testing';

import { FontsService } from './fonts.service';

describe('FontsService', () => {
  let service: FontsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FontsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

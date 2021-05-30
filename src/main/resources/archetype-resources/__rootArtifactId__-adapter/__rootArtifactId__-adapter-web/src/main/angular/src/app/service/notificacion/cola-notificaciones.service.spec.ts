import { TestBed } from '@angular/core/testing';

import { ColaNotificacionesService } from './cola-notificaciones.service';

describe('ColaNotificacionesService', () => {
  let service: ColaNotificacionesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ColaNotificacionesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

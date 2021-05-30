import { Component, OnInit } from '@angular/core';
import {MensajePagina} from '../../../model/mensaje-pagina';
import {ColaNotificacionesService} from '../../../service/notificacion/cola-notificaciones.service';
import {timer} from 'rxjs';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  mensajePagina: MensajePagina | undefined;

  constructor(
    private colaNotificaciones: ColaNotificacionesService
  ) {
    this.mensajePagina = colaNotificaciones.mensaje;
    this.programarCierreMensaje(3500);
  }

  ngOnInit(): void {
  }

  private programarCierreMensaje(tiempoMs: number): void {
    timer(tiempoMs).subscribe(value => {
      this.mensajePagina = undefined;
    });
  }
}

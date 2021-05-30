import { Injectable } from '@angular/core';
import {MensajePagina} from '../../model/mensaje-pagina';

@Injectable({
  providedIn: 'root'
})
export class ColaNotificacionesService {
  private vMensaje: MensajePagina | undefined;

  constructor() { }

  public get mensaje(): MensajePagina | undefined {
    const valor = this.vMensaje;
    this.vMensaje = undefined;
    return valor;
  }

  public set mensaje(valor: MensajePagina | undefined) {
    this.vMensaje = valor;
  }
}

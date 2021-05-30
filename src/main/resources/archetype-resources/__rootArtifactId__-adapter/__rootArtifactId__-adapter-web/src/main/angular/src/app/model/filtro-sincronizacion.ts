import {Configuracion} from './configuracion';

export interface FiltroSincronizacion {
  configuracionFuente: Configuracion;
  configuracionDestino: Configuracion;
  eliminarNoIncluidos: boolean;
  actualizarDiferentes: boolean;
}

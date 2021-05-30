import {Habilitacion} from './habilitacion';

export interface Aplicacion {
  nombre: string;
  descripcion: string | null | undefined;
  fechaRegistro: Date;
  habilitacion: Habilitacion;
}

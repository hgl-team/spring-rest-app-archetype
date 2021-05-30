import {Habilitacion} from './habilitacion';

export interface Ambiente {
  nombre: string;
  descripcion: string | null | undefined;
  fechaRegistro: Date;
  habilitacion: Habilitacion;
}

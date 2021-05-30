import {Habilitacion} from './habilitacion';

export interface Propiedad {
  id: number | null | undefined;
  aplicacion: string;
  ambiente: string;
  etiqueta: string;
  llave: string;
  valor: string;
  habilitacion: Habilitacion;
}

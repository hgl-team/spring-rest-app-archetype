import {Operacion} from './operacion';

export interface Menu {
  nombre: string;
  descripcion: string | undefined;
  icono: string | undefined;
  habilitado: boolean;
  rolesRequeridos: string[] | undefined;
  operacion: Operacion | undefined;
  menus: Menu[] | undefined;
}

import {FiltroPaginado} from './filtro-paginado';

export interface FiltroAplicacion extends FiltroPaginado {
  nombre: string | null | undefined;
  habilitado: boolean | string | null;
}

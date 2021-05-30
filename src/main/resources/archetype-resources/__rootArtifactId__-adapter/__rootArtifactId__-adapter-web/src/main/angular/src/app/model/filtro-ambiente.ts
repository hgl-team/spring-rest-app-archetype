import {FiltroPaginado} from './filtro-paginado';

export interface FiltroAmbiente extends FiltroPaginado {
  nombre: string | null | undefined;
  habilitado: boolean | string | null;
}

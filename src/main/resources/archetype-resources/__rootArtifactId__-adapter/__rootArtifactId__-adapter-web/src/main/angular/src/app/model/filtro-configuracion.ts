import {FiltroPaginado} from './filtro-paginado';

export interface FiltroConfiguracion extends FiltroPaginado {
  aplicacion: string | null | undefined;
  ambiente: string | null | undefined;
  etiqueta: string | null | undefined;
  habilitado: boolean | string | null;
}

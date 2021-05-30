export interface RespuestaFiltroPaginado<T> {
  paginado: boolean;
  pagina: number | null | undefined;
  tamanio: number | null | undefined;
  totalRegistros: number | null | undefined;
  resultado: T[] | null | undefined;
}

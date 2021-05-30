export interface Habilitacion {
  activo: boolean | string;
  habilitado: boolean | null | undefined;
  fechaActivoDesde: Date;
  fechaActivoHasta: Date | null | undefined;
}

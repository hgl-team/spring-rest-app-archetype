export interface OperacionSincronizacion {
  operacion: string;
  aplicar: boolean;
  propiedad: string;
  valorFuente?: string | null;
  valorDestino?: string | null;
}

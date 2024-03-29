import { Accommodation } from './accommodation';

export interface Opex {
  id: number;
  waterBill: number;
  gasBill: number;
  powerBill: number;
  showDetails?: boolean;
  accommodation: Accommodation;
}

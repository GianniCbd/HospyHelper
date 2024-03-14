import { Booking } from './booking';

export interface Accommodation {
  name: string;
  address: string;
  city: string;
  typeAccommodation: string;
  description: string;
  Booking: Booking;
}

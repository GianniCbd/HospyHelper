import { Accommodation } from './accommodation';
import { Room } from './room';

export interface Booking {
  id: number;
  fullName: string;
  email: string;
  phone: string;
  checkIn: Date;
  checkOut: Date;
  numberOfGuests: number;
  room: Room;
  accommodation: Accommodation;
}

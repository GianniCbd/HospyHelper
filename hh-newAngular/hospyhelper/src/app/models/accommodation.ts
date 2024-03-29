import { User } from './user';

export interface Accommodation {
  id: number;
  name: string;
  address: string;
  city: string;
  typeAccommodation: string;
  description: string;
  user: User;
}

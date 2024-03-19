import { RoomType } from './room-type';

export interface Room {
  id: number;
  number: number;
  price: number;
  capacity: number;
  roomType: RoomType;
}

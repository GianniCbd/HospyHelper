import { RoomType } from './room-type';

export interface Room {
  number: number;
  price: number;
  capacity: number;
  roomType: RoomType;
}

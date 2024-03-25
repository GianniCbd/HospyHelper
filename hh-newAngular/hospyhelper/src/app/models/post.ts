import { User } from './user';

export interface Post {
  id: number;
  title: string;
  content: string;
  creationDate: Date;
  likes: number;
  views: number;
  shares: number;
  user: User;
}

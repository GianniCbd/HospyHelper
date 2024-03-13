import { LoginRegisterDto } from './login-register-dto';

export interface UserDto extends LoginRegisterDto {
  id: string;
  email: string;
  password: string;
  confirmPassword: string;
  name: string;
  surname: string;
  image: string;
}

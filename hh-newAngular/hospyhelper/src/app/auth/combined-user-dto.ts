import { LoginRegisterDto } from './login-register-dto';

export interface CombinedUserDto extends LoginRegisterDto {
  name: string;
  surname: string;
  email: string;
  password: string;
  confirmPassword: string;
  image: string;
}

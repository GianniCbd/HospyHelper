export interface UserDto {
  accessToken: string;
  user: {
    id: string;
    email: string;
    password: string;
    confirmPassword: string;
    name: string;
    surname: string;
    image: string;
  };
}

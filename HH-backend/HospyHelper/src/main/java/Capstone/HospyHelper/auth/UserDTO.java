package Capstone.HospyHelper.auth;

import Capstone.HospyHelper.passwordMatches.PasswordMatches;


@PasswordMatches
public record UserDTO(
//        @NotEmpty(message = "Name must not be empty")
//        @Size(min = 3, message = "Name must be at least 3 characters long")
        String name,
//        @NotEmpty(message = "Surname must not be empty")
//        @Size(min = 3, message = "Surname must be at least 3 characters long")
        String surname,
//        @NotBlank(message = "Email cannot be blank")
//        @Email(message = "Invalid email format")
        String email,
//        @NotBlank(message = "Password cannot be blank")
//        @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters long")
        String password,
//        @NotBlank(message = "ConfirmPassword cannot be blank")
        String confirmPassword


) {
}
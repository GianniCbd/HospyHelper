package Capstone.HospyHelper.payloads;

import Capstone.HospyHelper.passwordMatches.PasswordMatches;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@PasswordMatches
public record UserDTO(
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email format")
   String email,
        @NotBlank(message = "Password cannot be blank")
        @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters long")
   String password,
        @NotBlank(message = "ConfirmPassword cannot be blank")
        String confirmPassword,
        @NotBlank(message = "Name must not be blank")
        @Size(min = 3, message = "Name must be at least 3 characters long")
   String name,
        @NotBlank(message = "Name must not be blank")
        @Size(min = 3, message = "Name must be at least 3 characters long")
   String surname

) {
}
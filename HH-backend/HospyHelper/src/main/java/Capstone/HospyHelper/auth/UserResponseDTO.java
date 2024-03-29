package Capstone.HospyHelper.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.UUID;

@JsonIgnoreProperties({"password", "confirmPassword"})
public record UserResponseDTO(
        UUID id,
        String name,
        String surname,
        String email,
        String password,
        String confirmPassword) {
}

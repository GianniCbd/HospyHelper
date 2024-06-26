package Capstone.HospyHelper.booking;

import Capstone.HospyHelper.accommodation.Accommodation;
import Capstone.HospyHelper.room.Room;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record BookingDTO(
        @NotBlank(message = "Full name cannot be empty")
       String fullName,
        @NotBlank(message = "Email cannot be empty")
        @Email(message = "Invalid email format")
       String email,
        @NotBlank(message = "Phone number cannot be empty")
       String phone,
        @NotNull(message = "Check-in date cannot be empty")
        @Future(message = "Check-in date must be in the future")
       LocalDate checkIn,
        @NotNull(message = "Check-out date cannot be empty")
        @Future(message = "Check-out date must be in the future")
       LocalDate checkOut,
       Room room,
        int numberOfGuests,
        Accommodation accommodation

) {
}

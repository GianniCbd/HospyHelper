package Capstone.HospyHelper.booking;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record BookingResponseDTO(

        String fullName,

        String email,

        String phone,

        LocalDate checkIn,

        LocalDate checkOut
) {
}

package Capstone.HospyHelper.booking;

import java.time.LocalDate;

public record BookingResponseDTO(

        String fullName,
        String email,
        String phone,
        LocalDate checkIn,
        LocalDate checkOut

) {
}

package Capstone.HospyHelper.booking;

import Capstone.HospyHelper.accommodation.Accommodation;
import Capstone.HospyHelper.room.Room;

import java.time.LocalDate;

public record BookingResponseDTO(

        String fullName,
        String email,
        String phone,
        LocalDate checkIn,
        LocalDate checkOut,
        Room room,
        Accommodation accommodation


) {
}

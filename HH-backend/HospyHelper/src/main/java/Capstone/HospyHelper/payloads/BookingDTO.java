package Capstone.HospyHelper.payloads;

import Capstone.HospyHelper.entities.Room;

public record BookingDTO(
       String fullName,
       String email,
       String phone,
       String checkIn,
       String checkOut,
       Room room
) {
}

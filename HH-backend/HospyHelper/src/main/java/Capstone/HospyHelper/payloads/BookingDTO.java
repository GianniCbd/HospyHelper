package Capstone.HospyHelper.payloads;

public record BookingDTO(
       String fullName,
       String email,
       String phone,
       String checkIn,
       String checkOut,
       long roomId

) {
}

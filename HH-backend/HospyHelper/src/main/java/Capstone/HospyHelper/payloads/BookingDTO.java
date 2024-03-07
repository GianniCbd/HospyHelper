package Capstone.HospyHelper.payloads;

import java.time.LocalDate;

public record BookingDTO(
       String fullName,
       String email,
       String phone,
//       @NotEmpty(message = "la data è obbligatoria")
       LocalDate checkIn,
//       @NotEmpty(message = "la data è obbligatoria")
       LocalDate checkOut,
       long roomId

) {
}

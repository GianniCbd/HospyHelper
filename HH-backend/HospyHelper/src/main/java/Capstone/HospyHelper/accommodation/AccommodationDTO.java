package Capstone.HospyHelper.accommodation;

import Capstone.HospyHelper.booking.Booking;

public record AccommodationDTO(
        String typeAccommodation,
        Booking booking
) {
}

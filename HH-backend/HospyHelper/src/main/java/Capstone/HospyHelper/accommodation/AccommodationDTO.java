package Capstone.HospyHelper.accommodation;

import Capstone.HospyHelper.booking.Booking;

public record AccommodationDTO(

        String name,
        String address,
        String city,
        String typeAccommodation,
        String description,
        Booking booking
) {
}

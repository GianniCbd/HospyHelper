package Capstone.HospyHelper.accommodation;

import Capstone.HospyHelper.auth.User;
import Capstone.HospyHelper.auth.UserDAO;
import Capstone.HospyHelper.booking.Booking;
import Capstone.HospyHelper.booking.BookingDAO;
import Capstone.HospyHelper.exceptions.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccommodationSRV {
    @Autowired
    AccommodationDAO accommodationDAO;
    @Autowired
    private BookingDAO bookingDAO;
    @Autowired
    private UserDAO userDAO;


    public Page<Accommodation> getAll(int pageNumber, int pageSize, String orderBy) {
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));
        return accommodationDAO.findAll(pageable);
    }

    public Accommodation saveAccommodation(AccommodationDTO ac, UUID userId){
        User user = userDAO.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
        Booking booking = bookingDAO.findById(ac.booking().getId()).orElseThrow(()-> new IllegalArgumentException("Invalid Booking id"));

        Accommodation accommodation = new Accommodation(ac.typeAccommodation(), booking,user);
        return accommodationDAO.save(accommodation);
}
    public Accommodation getAccommodationById(Long id) {
        return accommodationDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Accommodation updateAccommodation(Long id, AccommodationDTO ac) {
        Accommodation existingAccommodation = accommodationDAO.findById(id).orElseThrow(() -> new NotFoundException("Accommodation not found with ID: " + id));
        existingAccommodation.setTypeAccommodation(ac.typeAccommodation());
        return accommodationDAO.save(existingAccommodation);
    }
    public void deleteAccommodation(Long id) {
        Accommodation ac = this.getAccommodationById(id);
        accommodationDAO.delete(ac);
    }



}
package Capstone.HospyHelper.services;

import Capstone.HospyHelper.entities.Booking;
import Capstone.HospyHelper.exceptions.NotFoundException;
import Capstone.HospyHelper.payloads.BookingDTO;
import Capstone.HospyHelper.repositories.BookingDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class BookingSRV {

    @Autowired
    BookingDAO bookingDAO;


    public Page<Booking> getAll(int pageNumber, int pageSize, String orderBy) {
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));
        return bookingDAO.findAll(pageable);
    }
    public Booking saveBooking(BookingDTO bookingDTO) {
        Booking booking = new Booking(bookingDTO.fullName(), bookingDTO.email(), bookingDTO.phone(), bookingDTO.checkIn(),bookingDTO.checkOut(), bookingDTO.room());
        return bookingDAO.save(booking);
    }

    public Booking findById(UUID id) {
        return bookingDAO.findById(UUID.fromString(String.valueOf(id))).orElseThrow(() -> new NotFoundException(String.valueOf(id)));
    }

    public Booking updateBooking(UUID id, BookingDTO bookingDTO) {
        Booking existingBooking = bookingDAO.findById(id).orElseThrow(() -> new NotFoundException("Booking not found with ID: " + id));

        existingBooking.setFullName(bookingDTO.fullName());
        existingBooking.setEmail(bookingDTO.email());
        existingBooking.setPhone(bookingDTO.phone());
        existingBooking.setCheckIn(bookingDTO.checkIn());
        existingBooking.setCheckOut(bookingDTO.checkOut());
        existingBooking.setRoom(bookingDTO.room());
        return bookingDAO.save(existingBooking);
    }

    public void deleteById(UUID id) {
        bookingDAO.deleteById(id);
    }
}

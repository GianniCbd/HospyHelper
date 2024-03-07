package Capstone.HospyHelper.services;

import Capstone.HospyHelper.entities.Booking;
import Capstone.HospyHelper.entities.Room;
import Capstone.HospyHelper.entities.User;
import Capstone.HospyHelper.exceptions.NotFoundException;
import Capstone.HospyHelper.payloads.BookingDTO;
import Capstone.HospyHelper.payloads.BookingResponseDTO;
import Capstone.HospyHelper.repositories.BookingDAO;
import Capstone.HospyHelper.repositories.RoomDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class BookingSRV {

    @Autowired
    BookingDAO bookingDAO;
    @Autowired
    RoomDAO roomDAO;


    public Page<Booking> getAll(int pageNumber, int pageSize, String orderBy) {
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));
        return bookingDAO.findAll(pageable);
    }


    public BookingResponseDTO saveBooking(BookingDTO bookingDTO, User user) {
        Room room = roomDAO.findById(bookingDTO.roomId()).orElseThrow(() -> new NotFoundException(bookingDTO.roomId()));
        Booking booking = new Booking(bookingDTO.fullName(), bookingDTO.email(), bookingDTO.phone(), bookingDTO.checkIn(), bookingDTO.checkOut(), room, user);
        bookingDAO.save(booking);
        BookingResponseDTO responseDTO = new BookingResponseDTO(
                booking.getFullName(),
                booking.getEmail(),
                booking.getPhone(),
                booking.getCheckIn(),
                booking.getCheckOut()
        );

        return responseDTO;
    }

    public Booking findById(Long id) {
        return bookingDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public BookingResponseDTO updateBooking(Long id, BookingDTO bookingDTO) {
        Booking existingBooking = bookingDAO.findById(id).orElseThrow(() -> new NotFoundException("Booking not found with ID: " + id));
        existingBooking.setFullName(bookingDTO.fullName());
        existingBooking.setEmail(bookingDTO.email());
        existingBooking.setPhone(bookingDTO.phone());
        existingBooking.setCheckIn(bookingDTO.checkIn());
        existingBooking.setCheckOut(bookingDTO.checkOut());
        existingBooking = bookingDAO.save(existingBooking);
        BookingResponseDTO responseDTO = new BookingResponseDTO(
                existingBooking.getFullName(),
                existingBooking.getEmail(),
                existingBooking.getPhone(),
                existingBooking.getCheckIn(),
                existingBooking.getCheckOut()
        );
        return responseDTO;
    }

    public void deleteById(Long id) {
        bookingDAO.deleteById(id);
    }
}

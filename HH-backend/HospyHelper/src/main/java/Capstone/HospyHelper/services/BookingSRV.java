package Capstone.HospyHelper.services;

import Capstone.HospyHelper.entities.Booking;
import Capstone.HospyHelper.entities.Room;
import Capstone.HospyHelper.exceptions.NotFoundException;
import Capstone.HospyHelper.payloads.BookingDTO;
import Capstone.HospyHelper.payloads.BookingResponseDTO;
import Capstone.HospyHelper.repositories.AccommodationDAO;
import Capstone.HospyHelper.repositories.BookingDAO;
import Capstone.HospyHelper.repositories.RoomDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class BookingSRV {

    @Autowired
    BookingDAO bookingDAO;
    @Autowired
    RoomDAO roomDAO;
    @Autowired
    private AccommodationDAO accommodationDAO;


    public Page<Booking> getAll(int pageNumber, int pageSize, String orderBy) {
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));
        return bookingDAO.findAll(pageable);
    }


    public BookingResponseDTO saveBooking(BookingDTO bookingDTO) {
        Room room = roomDAO.findById(bookingDTO.roomId()).orElseThrow(() -> new NotFoundException(bookingDTO.roomId()));
        Booking booking = new Booking(bookingDTO.fullName(), bookingDTO.email(), bookingDTO.phone(), bookingDTO.checkIn(), bookingDTO.checkOut(), room);
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

    //*******************************************************************************
//    public List<Booking> findByEmail(String email) {
//        return bookingDAO.findByEmail(email);
//    }
//
//
//    public List<Booking> findByFullNameAndPhone(String fullName, String phone) {
//        return bookingDAO.findByFullNameAndPhone(fullName, phone);
//    }
//
//    public List<Booking> findByPartialName(String partialName) {
//        return bookingDAO.findByPartialName(partialName);
//    }

    public List<BookingResponseDTO> findByEmail(String email) {
        List<Booking> bookings = bookingDAO.findByEmail(email);
        return mapToDtoList(bookings);
    }

    public List<BookingResponseDTO> findByFullNameAndPhone(String fullName, String phone) {
        List<Booking> bookings = bookingDAO.findByFullNameAndPhone(fullName, phone);
        return mapToDtoList(bookings);
    }

    public List<BookingResponseDTO> findByPartialName(String partialName) {
        List<Booking> bookings = bookingDAO.findByPartialName(partialName);
        return mapToDtoList(bookings);
    }

    private List<BookingResponseDTO> mapToDtoList(List<Booking> bookings) {
        return bookings.stream()
                .map(booking -> new BookingResponseDTO(
                        booking.getFullName(),
                        booking.getEmail(),
                        booking.getPhone(),
                        booking.getCheckIn(),
                        booking.getCheckOut()
                ))
                .collect(Collectors.toList());
    }

}

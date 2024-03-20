package Capstone.HospyHelper.booking;

import Capstone.HospyHelper.accommodation.Accommodation;
import Capstone.HospyHelper.accommodation.AccommodationDAO;
import Capstone.HospyHelper.exceptions.NotFoundException;
import Capstone.HospyHelper.room.Room;
import Capstone.HospyHelper.room.RoomDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BookingSRV {
    @Autowired
    private AccommodationDAO accommodationDAO;

    @Autowired
    BookingDAO bookingDAO;
    @Autowired
    RoomDAO roomDAO;


    public Page<Booking> getAll(int pageNumber, int pageSize, String orderBy) {
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));
        return bookingDAO.findAll(pageable);
    }

    public BookingResponseDTO saveBooking(BookingDTO bookingDTO) {
        Room room = roomDAO.findById(bookingDTO.room().getId()).orElseThrow(() -> new IllegalArgumentException("Invalid Room id"));
        Accommodation accommodation = accommodationDAO.findById(bookingDTO.accommodation().getId()).orElseThrow(() -> new IllegalArgumentException("Invalid Accommodation id"));

        Booking booking = new Booking(bookingDTO.fullName(), bookingDTO.email(), bookingDTO.phone(), bookingDTO.checkIn(), bookingDTO.checkOut(), room, accommodation);
        bookingDAO.save(booking);
        BookingResponseDTO responseDTO = new BookingResponseDTO(
                booking.getFullName(),
                booking.getEmail(),
                booking.getPhone(),
                booking.getCheckIn(),
                booking.getCheckOut(),
                room,
                accommodation

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
                existingBooking.getCheckOut(),
                existingBooking.getRoom(),
                existingBooking.getAccommodation()

        );
        return responseDTO;
    }
    public void deleteById(Long id) {
        bookingDAO.deleteById(id);
    }

    //*******************************************************************************

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

    public List<BookingResponseDTO> findBookingsByCheckInBetween(LocalDate startCheckIn, LocalDate endCheckIn) {
        List<Booking> bookings = bookingDAO.findByCheckInBetween(startCheckIn, endCheckIn);
        return mapToDtoList(bookings);
    }

    public List<BookingResponseDTO> findBookingsByCheckOutBetween(LocalDate startCheckOut, LocalDate endCheckOut) {
        List<Booking> bookings = bookingDAO.findByCheckOutBetween(startCheckOut, endCheckOut);
        return mapToDtoList(bookings);
    }
    public Integer getTotalGuestsByDate(LocalDate targetDate) {
        return bookingDAO.getTotalGuestsByDate(targetDate);
    }

    private List<BookingResponseDTO> mapToDtoList(List<Booking> bookings) {
        return bookings.stream()
                .map(booking -> new BookingResponseDTO(
                        booking.getFullName(),
                        booking.getEmail(),
                        booking.getPhone(),
                        booking.getCheckIn(),
                        booking.getCheckOut(),
                        booking.getRoom(),
                        booking.getAccommodation()
                ))
                .collect(Collectors.toList());
    }


}

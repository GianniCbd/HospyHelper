package Capstone.HospyHelper.booking;

import Capstone.HospyHelper.exceptions.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/booking")
public class BookingCTRL {

    @Autowired
    BookingSRV bookingSRV;


    @GetMapping("/all")
    public Page<Booking> getAllBookings(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "orderBy", defaultValue = "id") String orderBy
    ) {
        try {
            return bookingSRV.getAllBookings(pageNumber, pageSize, orderBy);
        } catch (AccessDeniedException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated", e);
        }
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponseDTO saveBooking(@RequestBody @Validated BookingDTO bookingDTO, BindingResult validation) throws IOException {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }

        return this.bookingSRV.saveBooking(bookingDTO);
    }

    @GetMapping("/{id}")
    public Booking findById(@PathVariable Long id) {
        return this.bookingSRV.findById(id);
    }

    @PutMapping("/{id}")
    public BookingResponseDTO updateBooking(@PathVariable Long id, @RequestBody BookingDTO bookingDTO) {
        return bookingSRV.updateBooking(id,bookingDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable Long id) {
        bookingSRV.deleteById(id);
    }

    //***********************************************************************************************

    @GetMapping("/by-email")
    public ResponseEntity<List<BookingResponseDTO>> findByEmail(@RequestParam String email) {
        List<BookingResponseDTO> bookings = bookingSRV.findByEmail(email);
        return ResponseEntity.ok(bookings);
    }
    @GetMapping("/by-fullness-and-phone")
    public ResponseEntity<List<BookingResponseDTO>> findByFullNameAndPhone(@RequestParam String fullName,
                                                                           @RequestParam String phone) {
        List<BookingResponseDTO> bookings = bookingSRV.findByFullNameAndPhone(fullName, phone);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/findByPartialName/{partialName}")
    public List<BookingResponseDTO> findByPartialName(@PathVariable String partialName) {
        return bookingSRV.findByPartialName(partialName);
    }

    @GetMapping("/checkIn-between")
    public List<BookingResponseDTO> findBookingsByCheckInBetween(
            @RequestParam("startCheckIn") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startCheckIn,
            @RequestParam("endCheckIn") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endCheckIn) {
        return bookingSRV.findBookingsByCheckInBetween(startCheckIn, endCheckIn);
    }

    @GetMapping("/checkOut-between")
    public List<BookingResponseDTO> findBookingsByCheckOutBetween(
            @RequestParam("startCheckOut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startCheckOut,
            @RequestParam("endCheckOut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endCheckOut) {
        return bookingSRV.findBookingsByCheckOutBetween(startCheckOut, endCheckOut);
    }

    @GetMapping("/totalGuestsByDate")
    public Integer getTotalGuestsByDate(@RequestParam("targetDate") String targetDateString) {
        LocalDate targetDate = LocalDate.parse(targetDateString);
        return bookingSRV.getTotalGuestsByDate(targetDate);
    }
}

package Capstone.HospyHelper.controllers;

import Capstone.HospyHelper.entities.Booking;
import Capstone.HospyHelper.exceptions.BadRequestException;
import Capstone.HospyHelper.payloads.BookingDTO;
import Capstone.HospyHelper.payloads.BookingResponseDTO;
import Capstone.HospyHelper.services.BookingSRV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingCTRL {

    @Autowired
    BookingSRV bookingSRV;


    @GetMapping
    public Page<Booking> getAll(@RequestParam(defaultValue = "0") int pageNumber,
                                @RequestParam(defaultValue = "10") int pageSize,
                                @RequestParam(defaultValue = "name") String orderBy) {
        return bookingSRV.getAll(pageNumber, pageSize, orderBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponseDTO saveBooking(@RequestBody BookingDTO bookingDTO, BindingResult validation) {
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
}

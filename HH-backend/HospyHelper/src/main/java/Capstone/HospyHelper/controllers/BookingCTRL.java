package Capstone.HospyHelper.controllers;

import Capstone.HospyHelper.entities.Booking;
import Capstone.HospyHelper.entities.User;
import Capstone.HospyHelper.exceptions.BadRequestException;
import Capstone.HospyHelper.payloads.BookingDTO;
import Capstone.HospyHelper.repositories.RoomDAO;
import Capstone.HospyHelper.services.BookingSRV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
public class BookingCTRL {

    @Autowired
    BookingSRV bookingSRV;
    @Autowired
    private RoomDAO roomDAO;

    @GetMapping
    public Page<Booking> getAll(@RequestParam(defaultValue = "0") int pageNumber,
                                @RequestParam(defaultValue = "10") int pageSize,
                                @RequestParam(defaultValue = "name") String orderBy) {
        return bookingSRV.getAll(pageNumber, pageSize, orderBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Booking saveBooking(@RequestBody BookingDTO bookingDTO, @AuthenticationPrincipal User currentAuthenticatedUser, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.bookingSRV.saveBooking(bookingDTO,currentAuthenticatedUser);
    }

    @GetMapping("/{id}")
    public Booking findById(@PathVariable Long id) {
        return this.bookingSRV.findById(id);
    }

    @PutMapping("/{id}")
    public Booking updateBooking(@PathVariable Long id, @RequestBody BookingDTO bookingDTO) {
        return bookingSRV.updateBooking(id,bookingDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable Long id) {
        bookingSRV.deleteById(id);
    }
}

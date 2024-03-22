package Capstone.HospyHelper.booking;

import Capstone.HospyHelper.accommodation.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
public interface BookingDAO extends JpaRepository<Booking, Long> {


    @Query("SELECT b FROM Booking b WHERE b.email = :email")
    List<Booking> findByEmail(@Param("email") String email);
    @Query("SELECT b FROM Booking b WHERE b.fullName = :fullName AND b.phone = :phone")
    List<Booking> findByFullNameAndPhone(@Param("fullName") String fullName, @Param("phone") String phone);
    @Query("SELECT b FROM Booking b WHERE LOWER(b.fullName) LIKE LOWER(concat('%', :fullName, '%'))")
    List<Booking> findByPartialName(@Param("fullName") String fullName);
    List<Booking> findByCheckInBetween(LocalDate startCheckIn, LocalDate endCheckIn);
    List<Booking> findByCheckOutBetween(LocalDate startCheckOut, LocalDate endCheckOut);

    @Query("SELECT SUM(b.numberOfGuests) FROM Booking b WHERE b.checkIn = :targetDate OR b.checkOut = :targetDate")
    Integer getTotalGuestsByDate(@Param("targetDate") LocalDate targetDate);


    Collection<? extends Booking> findByAccommodation(Accommodation accommodation);


}

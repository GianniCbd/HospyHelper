package Capstone.HospyHelper.repositories;

import Capstone.HospyHelper.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingDAO extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b WHERE b.email = :email")
    List<Booking> findByEmail(@Param("email") String email);

    @Query("SELECT b FROM Booking b WHERE b.fullName = :fullName AND b.phone = :phone")
    List<Booking> findByFullNameAndPhone(@Param("fullName") String fullName, @Param("phone") String phone);

}

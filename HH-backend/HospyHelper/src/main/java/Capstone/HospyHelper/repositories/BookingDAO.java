package Capstone.HospyHelper.repositories;

import Capstone.HospyHelper.entities.Booking;
import Capstone.HospyHelper.entities.Room;
import Capstone.HospyHelper.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingDAO extends JpaRepository<Booking, Long> {
    @Query("SELECT b FROM Booking b WHERE b.user=:user AND b.checkIn=:checkIn")
    List<Booking> filterByUtenteAndDay(User user, String checkIn);
    List<Booking> findByRoom(Room room);


}

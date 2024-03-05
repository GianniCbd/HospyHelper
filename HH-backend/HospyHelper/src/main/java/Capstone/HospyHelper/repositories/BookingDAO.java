package Capstone.HospyHelper.repositories;

import Capstone.HospyHelper.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookingDAO extends JpaRepository<Booking, UUID> {

}

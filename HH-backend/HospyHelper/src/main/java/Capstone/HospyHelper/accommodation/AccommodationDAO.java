package Capstone.HospyHelper.accommodation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AccommodationDAO extends JpaRepository<Accommodation,Long> {

    List<Accommodation> getByUserId(UUID userId);
}

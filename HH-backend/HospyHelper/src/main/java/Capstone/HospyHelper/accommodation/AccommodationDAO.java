package Capstone.HospyHelper.accommodation;

import Capstone.HospyHelper.auth.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AccommodationDAO extends JpaRepository<Accommodation,Long> {

    List<Accommodation> getByUserId(UUID userId);

    Page<Accommodation> findByUser(User user, Pageable pageable);

}

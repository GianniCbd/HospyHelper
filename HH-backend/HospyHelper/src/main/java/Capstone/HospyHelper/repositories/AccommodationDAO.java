package Capstone.HospyHelper.repositories;

import Capstone.HospyHelper.entities.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccommodationDAO extends JpaRepository<Accommodation, UUID> {
    Optional<Accommodation> findByName(String name);
}

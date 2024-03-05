package Capstone.HospyHelper.repositories;

import Capstone.HospyHelper.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoomDAO extends JpaRepository<Room, UUID> {
}

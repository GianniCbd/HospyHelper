package Capstone.HospyHelper.repositories;

import Capstone.HospyHelper.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RoomDAO extends JpaRepository<Room, Long> {

}

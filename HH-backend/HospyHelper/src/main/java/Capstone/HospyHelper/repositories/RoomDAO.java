package Capstone.HospyHelper.repositories;

import Capstone.HospyHelper.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomDAO extends JpaRepository<Room, Long> {

    @Query("SELECT SUM(r.price) FROM Room r")
    Double getTotalPriceOfAllRooms();

    @Query("SELECT r FROM Room r ORDER BY r.price DESC")
    List<Room> getRoomsOrderByPriceDesc();

    @Query("SELECT r FROM Room r ORDER BY r.price ASC")
    List<Room> getRoomsOrderByPriceAsc();

    @Query("SELECT r FROM Room r ORDER BY r.number DESC")
    List<Room> getRoomsOrderByRoomNumberDesc();
    @Query("SELECT r FROM Room r ORDER BY r.number ASC")
    List<Room> getRoomsOrderByRoomNumberAsc();


}

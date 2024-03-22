package Capstone.HospyHelper.room;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

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

    Page<Room> findByOwnerId(UUID ownerId, Pageable pageable);


}

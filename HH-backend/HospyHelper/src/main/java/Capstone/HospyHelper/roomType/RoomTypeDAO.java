package Capstone.HospyHelper.roomType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoomTypeDAO extends JpaRepository<RoomType,Long> {

    Page<RoomType> findByOwnerId(UUID ownerId, Pageable pageable);


}

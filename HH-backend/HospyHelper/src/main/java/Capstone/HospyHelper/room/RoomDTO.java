package Capstone.HospyHelper.room;

import Capstone.HospyHelper.roomType.RoomType;

public record RoomDTO(
        Long id,
        int number,
        Double price,
        int capacity,
        RoomType roomType

) {
}

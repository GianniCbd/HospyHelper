package Capstone.HospyHelper.room;

import Capstone.HospyHelper.roomType.RoomType;

public record RoomDTO(
        int number,
        Double price,
        int capacity,
       RoomType roomType

) {
}

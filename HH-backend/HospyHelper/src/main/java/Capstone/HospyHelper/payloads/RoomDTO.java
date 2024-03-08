package Capstone.HospyHelper.payloads;

import Capstone.HospyHelper.entities.RoomType;

public record RoomDTO(
        int number,
        Double price,
        int maxCostumer,
       RoomType roomType

) {

}

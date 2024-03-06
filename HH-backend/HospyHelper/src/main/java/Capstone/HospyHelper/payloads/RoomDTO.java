package Capstone.HospyHelper.payloads;

import Capstone.HospyHelper.Enums.RoomType;

public record RoomDTO(
        int number,
        Double price,
        int maxCostumer,
        RoomType roomType

) {

}

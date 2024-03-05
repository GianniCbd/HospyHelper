package Capstone.HospyHelper.payloads;

import Capstone.HospyHelper.entities.Accommodation;
import Capstone.HospyHelper.entities.RoomType;

public record RoomDTO(
        int number,
        Double price,
        int maxCostumer,
        Accommodation accommodation,
        RoomType roomType

) {
}

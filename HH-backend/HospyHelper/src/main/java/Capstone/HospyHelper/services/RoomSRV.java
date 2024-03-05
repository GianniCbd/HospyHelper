package Capstone.HospyHelper.services;

import Capstone.HospyHelper.entities.Accommodation;
import Capstone.HospyHelper.entities.Room;
import Capstone.HospyHelper.exceptions.NotFoundException;
import Capstone.HospyHelper.payloads.RoomDTO;
import Capstone.HospyHelper.repositories.RoomDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class RoomSRV {

    @Autowired
    RoomDAO roomDAO;
    @Autowired
    private AccommodationSRV accommodationSRV;

    public Page<Room> getAll(int pageNumber, int pageSize, String orderBy) {
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));
        return roomDAO.findAll(pageable);
    }
    public Room saveRoom(RoomDTO roomDTO) {
        Accommodation accommodation = accommodationSRV.findByName(roomDTO.accommodation().getName());
        Room room = new Room(roomDTO.number(), roomDTO.price(), roomDTO.maxCostumer(),accommodation,roomDTO.roomType());
        return roomDAO.save(room);
    }
    public Room findById(UUID id) {
        return roomDAO.findById(UUID.fromString(String.valueOf(id))).orElseThrow(() -> new NotFoundException(String.valueOf(id)));
    }

    public Room updateBooking(UUID id, RoomDTO roomDTO) {
        Room existingRoom = roomDAO.findById(id).orElseThrow(() -> new NotFoundException("Room not found with ID: " + id));

        existingRoom.setNumber(roomDTO.number());
        existingRoom.setPrice(roomDTO.price());
        existingRoom.setMaxCustomer(roomDTO.maxCostumer());
        existingRoom.setAccommodation((roomDTO.accommodation()));
        existingRoom.setRoomType(roomDTO.roomType());
        return roomDAO.save(existingRoom);
    }

    public void deleteById(UUID id) {
        roomDAO.deleteById(id);
    }
}

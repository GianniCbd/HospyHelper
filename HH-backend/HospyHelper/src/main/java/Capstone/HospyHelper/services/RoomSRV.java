package Capstone.HospyHelper.services;

import Capstone.HospyHelper.entities.Room;
import Capstone.HospyHelper.entities.RoomType;
import Capstone.HospyHelper.exceptions.NotFoundException;
import Capstone.HospyHelper.payloads.RoomDTO;
import Capstone.HospyHelper.repositories.RoomDAO;
import Capstone.HospyHelper.repositories.RoomTypeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RoomSRV {


    @Autowired
    RoomDAO roomDAO;
    @Autowired
    RoomTypeDAO roomTypeDAO;

    public Page<Room> getAll(int pageNumber, int pageSize, String orderBy) {
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));
        return roomDAO.findAll(pageable);
    }
//    public Room saveRoom(RoomDTO newRoom) {
//        return roomDAO.save(
//                new Room(newRoom.number(), newRoom.price(), newRoom.maxCostumer(),newRoom.roomType())
//        );
//    }

    public Room saveRoom(RoomDTO roomDTO) {
        RoomType roomType = roomTypeDAO.findById(roomDTO.roomType().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid RoomType Id"));

        Room room = new Room(roomDTO.number(), roomDTO.price(), roomDTO.maxCostumer(), roomType);
        return roomDAO.save(room);
    }
    public Room getRoomById(Long id) {
        return roomDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Room updateBooking(Long id, RoomDTO roomDTO) {
        Room existingRoom = roomDAO.findById(id).orElseThrow(() -> new NotFoundException("Room not found with ID: " + id));
        existingRoom.setNumber(roomDTO.number());
        existingRoom.setPrice(roomDTO.price());
        existingRoom.setMaxCostumer(roomDTO.maxCostumer());
        return roomDAO.save(existingRoom);
    }
    public void deleteRoom(Long id) {
        Room room = this.getRoomById(id);
        roomDAO.delete(room);
    }

//****************************************************************************************//
    public Double getTotalPriceOfAllRooms() {
        return roomDAO.getTotalPriceOfAllRooms();
    }
    public List<Room> getRoomsOrderByPriceDesc() {
        return roomDAO.getRoomsOrderByPriceDesc();
    }
    public List<Room> getRoomsOrderByPriceAsc() {
        return roomDAO.getRoomsOrderByPriceAsc();
    }
    public List<Room> getRoomsOrderByRoomNumberDesc() {
        return roomDAO.getRoomsOrderByRoomNumberDesc();
    }
    public List<Room> getRoomsOrderByRoomNumberAsc() {
        return roomDAO.getRoomsOrderByRoomNumberAsc();
    }

}

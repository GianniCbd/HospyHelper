package Capstone.HospyHelper.room;

import Capstone.HospyHelper.accommodation.AccommodationDAO;
import Capstone.HospyHelper.auth.User;
import Capstone.HospyHelper.auth.UserDAO;
import Capstone.HospyHelper.booking.BookingDAO;
import Capstone.HospyHelper.exceptions.BadRequestException;
import Capstone.HospyHelper.exceptions.NotFoundException;
import Capstone.HospyHelper.roomType.RoomType;
import Capstone.HospyHelper.roomType.RoomTypeDAO;
import Capstone.HospyHelper.services.StatisticOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class RoomSRV {

    @Autowired
    StatisticOperation statisticOperation;
    @Autowired
    RoomDAO roomDAO;
    @Autowired
    RoomTypeDAO roomTypeDAO;
    @Autowired
    private AccommodationDAO accommodationDAO;
    @Autowired
    private BookingDAO bookingDAO;
    @Autowired
    private UserDAO userDAO;


    public Page<Room> getAllRoomsByOwnerId(UUID ownerId, int pageNumber, int pageSize, String orderBy) {
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));
        return roomDAO.findByOwnerId(ownerId, pageable);
    }


    public Room saveRoom(RoomDTO roomDTO, User user) {
        RoomType roomType = roomTypeDAO.findById(roomDTO.roomType().getId())
                .orElseThrow(() -> new BadRequestException("Invalid RoomType Id"));

        int capacity = roomDTO.capacity();
        if (capacity < 1 || capacity > 6) {
            throw new BadRequestException("Capacity must be between 1 to 6.");
        }
        Room room = new Room(roomDTO.number(), roomDTO.price(), roomDTO.capacity(), roomType, user);
        double calculatedPrice = statisticOperation.calculateRoomPrice(room);
        room.setPrice(calculatedPrice);
        return roomDAO.save(room);
    }


    public Room getRoomById(Long id) {
        return roomDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Room updateRoom(Long id, RoomDTO roomDTO) {
        Room existingRoom = roomDAO.findById(id).orElseThrow(() -> new NotFoundException("Room not found with ID: " + id));
        existingRoom.setNumber(roomDTO.number());
        existingRoom.setPrice(roomDTO.price());
        existingRoom.setCapacity(roomDTO.capacity());
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
    public Page<Room> getRoomsOrderByPriceDesc(int pageNumber, int pageSize) {
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("price").descending());
        return roomDAO.findAll(pageable);
    }

    public Page<Room> getRoomsOrderByPriceAsc(int pageNumber, int pageSize) {
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("price").ascending());
        return roomDAO.findAll(pageable);
    }

    public Page<Room> getRoomsOrderByRoomNumberDesc(int pageNumber, int pageSize) {
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("number").descending());
        return roomDAO.findAll(pageable);
    }

    public Page<Room> getRoomsOrderByRoomNumberAsc(int pageNumber, int pageSize) {
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("number").ascending());
        return roomDAO.findAll(pageable);
    }


}

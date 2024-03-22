package Capstone.HospyHelper.room;

import Capstone.HospyHelper.auth.User;
import Capstone.HospyHelper.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomCTRL {

    private final RoomSRV roomSRV;

    @Autowired
    public RoomCTRL(RoomSRV roomSrv) {
        this.roomSRV = roomSrv;
    }


    @GetMapping
    public Page<Room> getAll(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String orderBy) {

        return roomSRV.getAll( pageNumber, pageSize, orderBy);
    }

    private User getUser() {
        return new User();
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Room> saveRoom(@RequestBody @Validated RoomDTO roomDTO, BindingResult validation) throws IOException {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }

        Room savedRoom = roomSRV.saveRoom(roomDTO);

        return new ResponseEntity<>(savedRoom, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Room findById(@PathVariable Long id) {
        return this.roomSRV.getRoomById(id);
    }

    @PutMapping("/{id}")
    public Room updateAccommodation(@PathVariable Long id, @RequestBody RoomDTO roomDTO) {
        return roomSRV.updateRoom(id,roomDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable Long id) {
        roomSRV.deleteRoom(id);
    }

//******************************************************************************************//
    @GetMapping("/total-price")
    public ResponseEntity<Double> getTotalPriceOfAllRooms() {
        Double totalPrice = roomSRV.getTotalPriceOfAllRooms();
        return ResponseEntity.ok(totalPrice);
    }

    @GetMapping("/order-by-price-desc")
    public ResponseEntity<List<Room>> getRoomsOrderByPriceDesc() {
        List<Room> rooms = roomSRV.getRoomsOrderByPriceDesc();
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }
    @GetMapping("/order-by-price-asc")
    public ResponseEntity<List<Room>> getRoomsOrderByPriceAsc() {
        List<Room> rooms = roomSRV.getRoomsOrderByPriceAsc();
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @GetMapping("/order-by-room-number-desc")
    public ResponseEntity<List<Room>> getRoomsOrderByRoomNumberDesc() {
        List<Room> rooms = roomSRV.getRoomsOrderByRoomNumberDesc();
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }
    @GetMapping("/order-by-room-number-asc")
    public ResponseEntity<List<Room>> getRoomsOrderByRoomNumberAsc() {
        List<Room> rooms = roomSRV.getRoomsOrderByRoomNumberAsc();
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

}
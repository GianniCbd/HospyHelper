package Capstone.HospyHelper.room;

import Capstone.HospyHelper.auth.User;
import Capstone.HospyHelper.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/room")
public class RoomCTRL {

    private final RoomSRV roomSRV;

    @Autowired
    public RoomCTRL(RoomSRV roomSrv) {
        this.roomSRV = roomSrv;
    }

    @GetMapping
    public ResponseEntity<Page<Room>> getUserRooms(@AuthenticationPrincipal User currentAuthenticatedUser,
                                                   @RequestParam(defaultValue = "0") int pageNumber,
                                                   @RequestParam(defaultValue = "4") int pageSize,
                                                   @RequestParam(defaultValue = "id") String orderBy)
                                                   {
        Page<Room> userRoomsPage = roomSRV.getAllRoomsByOwnerId(currentAuthenticatedUser.getId(), pageNumber, pageSize,orderBy);
        System.out.println(userRoomsPage);
        return new ResponseEntity<>(userRoomsPage, HttpStatus.OK);
    }


    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Room> saveRoom(@AuthenticationPrincipal User currentAuthenticatedUser, @RequestBody @Validated RoomDTO roomDTO, BindingResult validation) throws IOException {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        Room savedRoom = roomSRV.saveRoom(roomDTO, currentAuthenticatedUser);
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

    @GetMapping("/orderByPriceDesc")
    public Page<Room> getRoomsOrderByPriceDesc(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        if (pageNumber > 20) pageSize = 20;
        return roomSRV.getRoomsOrderByPriceDesc(pageNumber, pageSize);
    }

    @GetMapping("/orderByPriceAsc")
    public Page<Room> getRoomsOrderByPriceAsc(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        if (pageNumber > 20) pageSize = 20;
        return roomSRV.getRoomsOrderByPriceAsc(pageNumber, pageSize);
    }

    @GetMapping("/orderByRoomNumberDesc")
    public Page<Room> getRoomsOrderByRoomNumberDesc(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        if (pageNumber > 20) pageSize = 20;
        return roomSRV.getRoomsOrderByRoomNumberDesc(pageNumber, pageSize);
    }

    @GetMapping("/orderByRoomNumberAsc")
    public Page<Room> getRoomsOrderByRoomNumberAsc(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        if (pageNumber > 20) pageSize = 20;
        return roomSRV.getRoomsOrderByRoomNumberAsc(pageNumber, pageSize);
    }

}

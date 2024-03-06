package Capstone.HospyHelper.controllers;

import Capstone.HospyHelper.entities.Room;
import Capstone.HospyHelper.exceptions.BadRequestException;
import Capstone.HospyHelper.payloads.RoomDTO;
import Capstone.HospyHelper.services.RoomSRV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
public class RoomCTRL {

    private final RoomSRV roomSRV;

    @Autowired
    public RoomCTRL(RoomSRV roomSrv) {
        this.roomSRV = roomSrv;
    }
//    @Autowired
//    RoomSRV roomSRV;


    @GetMapping
    public Page<Room> getAll(@RequestParam(defaultValue = "0") int pageNumber,
                             @RequestParam(defaultValue = "10") int pageSize,
                             @RequestParam(defaultValue = "name") String orderBy) {
        return roomSRV.getAll(pageNumber, pageSize, orderBy);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Room saveRoom(@RequestBody RoomDTO roomDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.roomSRV.saveRoom(roomDTO);
    }



    @GetMapping("/{id}")
    public Room findById(@PathVariable Long id) {
        return this.roomSRV.getRoomById(id);
    }

    @PutMapping("/{id}")
    public Room updateBooking(@PathVariable Long id, @RequestBody RoomDTO roomDTO) {
        return roomSRV.updateBooking(id,roomDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable Long id) {
        roomSRV.deleteRoom(id);
    }
}

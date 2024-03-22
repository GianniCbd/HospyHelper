package Capstone.HospyHelper.roomType;

import Capstone.HospyHelper.auth.User;
import Capstone.HospyHelper.exceptions.BadRequestException;
import Capstone.HospyHelper.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/roomTypes")
public class RoomTypeCTRL {
    @Autowired
    private RoomTypeSRV roomTypeService;


@GetMapping("/all")
public ResponseEntity<Page<RoomType>> getAllRoomTypesByOwnerId(
        @AuthenticationPrincipal User currentAuthenticatedUser,
        @RequestParam(defaultValue = "0") int pageNumber,
        @RequestParam(defaultValue = "10") int pageSize,
        @RequestParam(defaultValue = "typeName") String orderBy) {

    Page<RoomType> roomTypesPage = roomTypeService.getAllRoomsTypesByOwnerId(currentAuthenticatedUser.getId(), pageNumber, pageSize, orderBy);

    return new ResponseEntity<>(roomTypesPage, HttpStatus.OK);
}
    @GetMapping("/{id}")
    public ResponseEntity<RoomType> getRoomTypeById(@PathVariable Long id) {
        RoomType roomType = roomTypeService.getRoomTypeById(id);
        return ResponseEntity.ok(roomType);
    }
    @PostMapping
    public ResponseEntity<RoomType> createRoomType(@AuthenticationPrincipal User currentAuthenticatedUser,@RequestBody @Validated RoomTypeDTO newRoomType, BindingResult validation) throws IOException {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        RoomType savedRoomType = roomTypeService.saveRoomType(currentAuthenticatedUser.getId(),newRoomType,currentAuthenticatedUser);
        return new ResponseEntity<>(savedRoomType, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<RoomType> updateRoomType(@PathVariable Long id, @RequestBody RoomTypeDTO roomTypeDTO) {
        try {
            RoomType updatedRoomType = roomTypeService.updateRoomType(id, roomTypeDTO);
            return new ResponseEntity<>(updatedRoomType, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoomType(@PathVariable Long id) {
        try {
            roomTypeService.deleteRoomType(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //    @PostMapping("/upload")
//    public String uploadImage(@RequestParam("image")MultipartFile image) throws IOException {
//        return this.roomTypeService.UploadImage(image);
//    }
    @PatchMapping("/upload/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadAvatar(@PathVariable Long id, @RequestParam("image") MultipartFile image) throws IOException {
        return this.roomTypeService.UploadImage(id, image);
    }
}
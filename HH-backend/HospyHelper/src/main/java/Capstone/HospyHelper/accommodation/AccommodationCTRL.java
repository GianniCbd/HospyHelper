package Capstone.HospyHelper.accommodation;

import Capstone.HospyHelper.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/accommodation")
public class AccommodationCTRL {

    @Autowired
    AccommodationSRV accommodationSRV;

    @PostMapping("/save/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Accommodation> saveAccommodation(@RequestBody AccommodationDTO accommodationDTO, @PathVariable UUID userId, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        Accommodation savedAccommodation = accommodationSRV.saveAccommodation(accommodationDTO,userId);
        return new ResponseEntity<>(savedAccommodation, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Accommodation findById(@PathVariable Long id) {
        return this.accommodationSRV.getAccommodationById(id);
    }

    @PutMapping("/{id}")
    public Accommodation updateAccommodation(@PathVariable Long id, @RequestBody AccommodationDTO accommodationDTO) {
        return accommodationSRV.updateAccommodation(id,accommodationDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteAccommodation(@PathVariable Long id) {
        accommodationSRV.deleteAccommodation(id);
    }
}

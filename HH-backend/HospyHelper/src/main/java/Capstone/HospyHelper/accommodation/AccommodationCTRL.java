package Capstone.HospyHelper.accommodation;

import Capstone.HospyHelper.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/accommodation")
public class AccommodationCTRL {

    @Autowired
    AccommodationSRV accommodationSRV;

    @GetMapping
    public Page<Accommodation> getAll(@RequestParam(defaultValue = "0") int pageNumber,
                             @RequestParam(defaultValue = "10") int pageSize,
                             @RequestParam(defaultValue = "name") String orderBy) {
        return accommodationSRV.getAll(pageNumber, pageSize, orderBy);
    }

    @GetMapping("/byUser/{userId}")
    public ResponseEntity<List<Accommodation>> getAccommodationByUserId(@PathVariable UUID userId) {
        List<Accommodation> accommodations = accommodationSRV.getByUserId(userId);
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }

    @PostMapping("/save/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Accommodation> saveAccommodation(@RequestBody AccommodationDTO accommodationDTO, @PathVariable UUID userId,  BindingResult validation) throws IOException {
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

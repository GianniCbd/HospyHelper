package Capstone.HospyHelper.accommodation;

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

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/accommodation")
public class AccommodationCTRL {

    @Autowired
    AccommodationSRV accommodationSRV;

    @GetMapping
    public ResponseEntity<Page<Accommodation>> getAllAccommodations(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String orderBy) {
        try {
            Page<Accommodation> userAccommodations = accommodationSRV.getAll(pageNumber, pageSize, orderBy);
            return ResponseEntity.ok(userAccommodations);
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @GetMapping("/byUser/{userId}")
    public ResponseEntity<List<Accommodation>> getAccommodationByUserId(@PathVariable UUID userId) {
        List<Accommodation> accommodations = accommodationSRV.getByUserId(userId);
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Accommodation save(@AuthenticationPrincipal User currentAuthenticatedUser, @RequestBody @Validated AccommodationDTO accommodationDTO, BindingResult validation) {
        if (validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.accommodationSRV.saveAccommodation( accommodationDTO,currentAuthenticatedUser.getId());
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

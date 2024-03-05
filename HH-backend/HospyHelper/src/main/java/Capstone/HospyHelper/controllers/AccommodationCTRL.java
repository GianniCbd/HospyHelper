package Capstone.HospyHelper.controllers;

import Capstone.HospyHelper.entities.Accommodation;
import Capstone.HospyHelper.exceptions.BadRequestException;
import Capstone.HospyHelper.payloads.AccommodationDTO;
import Capstone.HospyHelper.services.AccommodationSRV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Accommodation saveRoom(@RequestBody AccommodationDTO acDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.accommodationSRV.save(acDTO);
    }
    @GetMapping("/{id}")
    public Accommodation findById(@PathVariable UUID id) {
        return this.accommodationSRV.findById(id);
    }

    @PutMapping("/{id}")
    public Accommodation updateAcc(@PathVariable UUID id, @RequestBody AccommodationDTO acDTO) {
        return accommodationSRV.updateAccom(id,acDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        accommodationSRV.deleteById(id);
    }

}

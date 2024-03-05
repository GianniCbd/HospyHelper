package Capstone.HospyHelper.services;

import Capstone.HospyHelper.entities.Accommodation;
import Capstone.HospyHelper.exceptions.NotFoundException;
import Capstone.HospyHelper.payloads.AccommodationDTO;
import Capstone.HospyHelper.repositories.AccommodationDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class AccommodationSRV {

    @Autowired
    AccommodationDAO accommodationDAO;


    public Page<Accommodation> getAll(int pageNumber, int pageSize, String orderBy) {
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));
        return accommodationDAO.findAll(pageable);
    }
    public Accommodation save(AccommodationDTO ac) {
Accommodation accommodation = new Accommodation(ac.name(), ac.address(), ac.city());
    return accommodationDAO.save(accommodation);
    }
    public Accommodation findById(UUID id) {
        return accommodationDAO.findById(UUID.fromString(String.valueOf(id))).orElseThrow(() -> new NotFoundException(String.valueOf(id)));
    }

    public Accommodation findByName(String name){
        return accommodationDAO.findByName(name).orElseThrow(()-> new NotFoundException("customer non trovato"));
    }

    public Accommodation updateAccom (UUID id, AccommodationDTO ac){
        Accommodation existingAccom = accommodationDAO.findById(id).orElseThrow(()->new NotFoundException("Accommodation not found with ID: " + id));
        existingAccom.setName(ac.name());
        existingAccom.setAddress(ac.address());
        existingAccom.setCity(ac.city());
        return  accommodationDAO.save(existingAccom);
    }

    public void deleteById(UUID id) {
        accommodationDAO.deleteById(id);
    }

}

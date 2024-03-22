package Capstone.HospyHelper.opex;

import Capstone.HospyHelper.accommodation.Accommodation;
import Capstone.HospyHelper.accommodation.AccommodationDAO;
import Capstone.HospyHelper.auth.User;
import Capstone.HospyHelper.exceptions.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OperationExpensesSRV {

    @Autowired
    OperationExpensesDAO operationExpensesDAO;

    @Autowired
    private AccommodationDAO accommodationDAO;


    public Page<OperationExpenses> getAll(int pageNumber, int pageSize, String orderBy) throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = null;
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            currentUser = (User) authentication.getPrincipal();
        } else {
            throw new IllegalStateException("User not authenticated");
        }
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));
        Page<Accommodation> userAccommodations = accommodationDAO.findByUser(currentUser, pageable);
        List<OperationExpenses> userOperationExpenses = new ArrayList<>();
        for (Accommodation accommodation : userAccommodations) {
            userOperationExpenses.addAll(operationExpensesDAO.findByAccommodation(accommodation));
        }
        int start = (int) pageable.getOffset();
        int end = (start + pageable.getPageSize()) > userOperationExpenses.size() ? userOperationExpenses.size() : (start + pageable.getPageSize());
        Page<OperationExpenses> userOperationExpensesPage = new PageImpl<>(userOperationExpenses.subList(start, end), pageable, userOperationExpenses.size());
        return userOperationExpensesPage;
    }

    public OperationExpenses saveOperationExpenses(OperationExpensesDTO operationExpensesDTO, Long accommodation_id) {
        Accommodation accommodation = accommodationDAO.findById(accommodation_id)
                .orElseThrow(() -> new EntityNotFoundException("Accommodation with id " + accommodation_id + " not found"));

        OperationExpenses operationExpenses = new OperationExpenses(
                operationExpensesDTO.waterBill(),
                operationExpensesDTO.gasBill(),
                operationExpensesDTO.powerBill(),
                accommodation
        );
        return operationExpensesDAO.save(operationExpenses);
    }

    public OperationExpenses getOpexById(Long id) {
        return operationExpensesDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }
    public OperationExpenses updateOpex(Long id, OperationExpensesDTO op) {
        OperationExpenses existingOpex = operationExpensesDAO.findById(id).orElseThrow(() -> new NotFoundException("Room not found with ID: " + id));
        existingOpex.setWaterBill(op.waterBill());
        existingOpex.setGasBill(op.gasBill());
        existingOpex.setPowerBill(op.powerBill());

        return operationExpensesDAO.save(existingOpex);
    }
    public void deleteOpex(Long id) {
        OperationExpenses operationExpenses = this.getOpexById(id);
        operationExpensesDAO.delete(operationExpenses);
    }

    //*********************************************

    public int calculateTotalExpenses() {
        return operationExpensesDAO.calculateTotalExpenses();
    }

}

package Capstone.HospyHelper.opex;

import Capstone.HospyHelper.accommodation.Accommodation;
import Capstone.HospyHelper.accommodation.AccommodationDAO;
import Capstone.HospyHelper.exceptions.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class OperationExpensesSRV {

    @Autowired
    OperationExpensesDAO operationExpensesDAO;

    @Autowired
    private AccommodationDAO accommodationDAO;


    public Page<OperationExpenses> getAll(int pageNumber, int pageSize, String orderBy) {
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));
        return operationExpensesDAO.findAll(pageable);
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

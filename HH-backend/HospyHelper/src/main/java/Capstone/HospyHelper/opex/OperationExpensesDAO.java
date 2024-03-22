package Capstone.HospyHelper.opex;

import Capstone.HospyHelper.accommodation.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface OperationExpensesDAO extends JpaRepository<OperationExpenses,Long> {

        @Query("SELECT SUM(o.waterBill + o.gasBill + o.powerBill) FROM OperationExpenses o")
        int calculateTotalExpenses();

    Collection<? extends OperationExpenses> findByAccommodation(Accommodation accommodation);
}

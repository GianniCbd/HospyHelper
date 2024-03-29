package Capstone.HospyHelper.opex;

import Capstone.HospyHelper.accommodation.Accommodation;

public record OperationExpensesDTO(
        long id,
        int waterBill,
        int gasBill,
        int powerBill,
        Accommodation accommodation

) {}

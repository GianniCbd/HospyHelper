package Capstone.HospyHelper.opex;

import Capstone.HospyHelper.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/operation-expenses")
public class OperationExpensesCTRL {


@Autowired
OperationExpensesSRV operationExpensesSRV;

    @GetMapping
    public Page<OperationExpenses> getAll(@RequestParam(defaultValue = "0") int pageNumber,
                                 @RequestParam(defaultValue = "10") int pageSize,
                                 @RequestParam(defaultValue = "waterBill") String orderBy) {
        return operationExpensesSRV.getAll(pageNumber, pageSize, orderBy);
    }

    @PostMapping("/save/{accommodation_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OperationExpenses> saveOpex(@RequestBody @Validated OperationExpensesDTO operationExpensesDTO, @PathVariable Long accommodation_id, BindingResult validation) throws IOException {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        OperationExpenses savedOpex = operationExpensesSRV.saveOperationExpenses(operationExpensesDTO, accommodation_id);
        return ResponseEntity.ok(savedOpex);
    }

    @GetMapping("/{id}")
    public OperationExpenses findById(@PathVariable Long id) {
        return this.operationExpensesSRV.getOpexById(id);
    }

    @PutMapping("/{id}")
    public OperationExpenses updateOpex(@PathVariable Long id, @RequestBody OperationExpensesDTO op) {
        return operationExpensesSRV.updateOpex(id,op);
    }

    @DeleteMapping("/{id}")
    public void deleteOpex(@PathVariable Long id) {
        operationExpensesSRV.deleteOpex(id);
    }


    //******************************
    @GetMapping("/total-expenses")
    public int calculateTotalExpenses() {
        return operationExpensesSRV.calculateTotalExpenses();
    }
}

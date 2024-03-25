package Capstone.HospyHelper.opex;

import Capstone.HospyHelper.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/operation-expenses")
public class OperationExpensesCTRL {


@Autowired
OperationExpensesSRV operationExpensesSRV;

    @GetMapping
    public Page<OperationExpenses> getAllAccommodations(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "orderBy", defaultValue = "id") String orderBy
    ) {
        try {
            return operationExpensesSRV.getAll(pageNumber, pageSize, orderBy);
        } catch (AccessDeniedException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated", e);
        }
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OperationExpenses> saveOpex(@RequestBody @Validated OperationExpensesDTO operationExpensesDTO, BindingResult validation) throws IOException {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        OperationExpenses savedOpex = operationExpensesSRV.saveOperationExpenses(operationExpensesDTO);
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

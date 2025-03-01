package ExpensesDashboard.Adapters.Controller;

import ExpensesDashboard.Application.DTOs.ExpenseRequestDto;
import ExpensesDashboard.Application.DTOs.UpdateExpenseRequestDto;
import ExpensesDashboard.Infra.Data.ExpenseRepository;
import ExpensesDashboard.Infra.Data.UserRepository;
import ExpensesDashboard.Domain.Entities.Expense;
import ExpensesDashboard.Application.Services.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expense")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseRepository _expenseRepository;
    private final UserRepository _userRepository;
    private final ExpenseService service;

    @GetMapping
    public ResponseEntity getExpenses(){
        List<Expense> expenses = _expenseRepository.findAll();

        if(expenses.isEmpty()){
            return ResponseEntity.badRequest().body("No tips found");
        }
        return ResponseEntity.ok(expenses);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody ExpenseRequestDto body){
        var result = service.create(body);

        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity update(@RequestBody UpdateExpenseRequestDto body){
        var result = service.UpdateExpense(body.Id().toString(), body);

        return ResponseEntity.ok(result.id);
    }
}

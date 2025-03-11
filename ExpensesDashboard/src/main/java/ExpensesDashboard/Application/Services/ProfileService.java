package ExpensesDashboard.Application.Services;

import ExpensesDashboard.Application.DTOs.LoggedUserExpenseDto;
import ExpensesDashboard.Application.DTOs.LoggedUserResponseDto;
import ExpensesDashboard.Infra.Data.ExpenseRepository;
import ExpensesDashboard.Infra.Data.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProfileService {
    private final UserRepository userRepository;

    public LoggedUserResponseDto getLoggedUserData(String userId){
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        var expenses = user.getExpenses().stream().map(e ->
                new LoggedUserExpenseDto(e.getName(), e.getType(), e.getAmount())).toList();

        var totalPayed = expenses.stream()
                .mapToDouble(LoggedUserExpenseDto::amount)
                .sum();

        var remainingAmount = user.getSalary() - totalPayed;

        return new LoggedUserResponseDto(user.getSalary(), totalPayed, remainingAmount, expenses);
    }
}

package ExpensesDashboard.Application.DTOs;
import java.util.List;

public record LoggedUserResponseDto (Double salary, Double totalPayed, Double remainingAmount,
                                     List<LoggedUserExpenseDto> expense) {
}
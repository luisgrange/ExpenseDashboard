package ExpensesDashboard.Application.DTOs;

import ExpensesDashboard.Domain.Enums.ExpenseType;

import java.util.UUID;

public record UpdateExpenseRequestDto(String name,
                                      ExpenseType type,
                                      Double amount,
                                      String userEmail) {
}

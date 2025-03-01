package ExpensesDashboard.Application.DTOs;

import ExpensesDashboard.Domain.Enums.ExpenseType;

import java.util.UUID;

public record UpdateExpenseRequestDto(UUID Id, String name,
                                      ExpenseType type,
                                      Double amount,
                                      String userEmail) {
}

package ExpensesDashboard.Application.DTOs;

import ExpensesDashboard.Domain.Enums.ExpenseType;

public record ExpenseRequestDto (
        String name,
        ExpenseType type,
        Double amount,
        String userEmail
) {}
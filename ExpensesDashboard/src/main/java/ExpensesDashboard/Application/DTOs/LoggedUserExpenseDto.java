package ExpensesDashboard.Application.DTOs;

import ExpensesDashboard.Domain.Enums.ExpenseType;

public record LoggedUserExpenseDto(String name, ExpenseType type, Double amount) { }

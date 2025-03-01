package ExpensesDashboard.Application.DTOs;

public record RegisterRequestDto(String name, String email, String role,String password, Double salary) {
}

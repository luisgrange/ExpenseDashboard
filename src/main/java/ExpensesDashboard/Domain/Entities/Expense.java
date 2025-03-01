package ExpensesDashboard.Domain.Entities;

import ExpensesDashboard.Domain.Enums.ExpenseType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;
    public String name;
    public ExpenseType type;
    public Double amount;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

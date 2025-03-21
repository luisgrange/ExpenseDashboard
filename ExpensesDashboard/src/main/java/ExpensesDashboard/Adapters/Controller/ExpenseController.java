package ExpensesDashboard.Adapters.Controller;

import ExpensesDashboard.Application.DTOs.ExpenseRequestDto;
import ExpensesDashboard.Application.DTOs.UpdateExpenseRequestDto;
import ExpensesDashboard.Infra.Data.ExpenseRepository;
import ExpensesDashboard.Infra.Data.UserRepository;
import ExpensesDashboard.Domain.Entities.Expense;
import ExpensesDashboard.Application.Services.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expense")
@RequiredArgsConstructor
@Tag(name = "Despesas", description = "Gerenciamento de despesas dos usuários")
public class ExpenseController {
    private final ExpenseService service;

    @Operation(
            summary = "Listar todas as despesas",
            description = "Retorna todas as despesas cadastradas no sistema. Se nenhuma despesa for encontrada, retorna um erro."
    )
    @ApiResponse(responseCode = "200", description = "Lista de despesas retornada com sucesso")
    @ApiResponse(responseCode = "404", description = "Nenhuma despesa encontrada")
    @ApiResponse(responseCode = "401", description = "Autenticação necessária")
    @GetMapping
    public ResponseEntity getExpenses(){
        List<Expense> expenses = service.getExpenses();

        if(expenses.isEmpty()){
            return ResponseEntity.badRequest().body("No tips found");
        }
        return ResponseEntity.ok(expenses);
    }

    @Operation(
            summary = "Cadastrar uma nova despesa",
            description = "Cria um novo registro de despesa para o usuário logado."
    )
    @ApiResponse(responseCode = "201", description = "Despesa cadastrada com sucesso")
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
    @ApiResponse(responseCode = "401", description = "Autenticação necessária")
    @PostMapping
    public ResponseEntity create(@RequestBody ExpenseRequestDto body){
        var result = service.create(body);

        return ResponseEntity.ok().build();
    }


    @Operation(
            summary = "Atualizar uma despesa existente",
            description = "Atualiza as informações de uma despesa cadastrada com base no ID informado. O usuário vinculado não pode ser alterado."
    )
    @ApiResponse(responseCode = "200", description = "Despesa atualizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Despesa não encontrada")
    @ApiResponse(responseCode = "400", description = "ID inválido ou erro na requisição")
    @ApiResponse(responseCode = "401", description = "Autenticação necessária")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(HttpServletRequest request, @RequestBody UpdateExpenseRequestDto body) {
        var loggedUserId = (String) request.getAttribute("userId");

        if(loggedUserId == null)
            return ResponseEntity.status(401).build();

        var result = service.UpdateExpense(loggedUserId, body);
        return ResponseEntity.ok(result.id);
    }

    @Operation(
            summary = "Exclusão de despesa com base no ID",
            description = "Exclui uma despesa pelo seu ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Despesa excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Despesa não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @DeleteMapping("/expense/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable String expenseId) {
        boolean isDeleted = service.deleteExpense(expenseId);

        if (isDeleted)
            return ResponseEntity.noContent().build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(
            summary = "Busca por despesas com base na categoria",
            description = "Busca de informações das despesas com base em sua categoria"
    )
    @ApiResponse(responseCode = "401", description = "Autenticação necessária")
    @GetMapping("/filter/type")
    public ResponseEntity getByType(@RequestParam String type){
        var response = service.getByType(type);

        return ResponseEntity.ok(response);
    }
}

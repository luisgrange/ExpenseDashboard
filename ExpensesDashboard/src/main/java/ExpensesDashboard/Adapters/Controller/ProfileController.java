package ExpensesDashboard.Adapters.Controller;

import ExpensesDashboard.Application.Services.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Dashboard", description = "Visualização principal do usuário")
public class ProfileController {

    private final ProfileService service;

    @Operation(
            summary = "Obtém o resumo financeiro do usuário logado",
            description = "Retorna o salário, total gasto e lista de despesas do usuário autenticado."
    )
    @ApiResponse(responseCode = "200", description = "Dados retornados com sucesso")
    @ApiResponse(responseCode = "401", description = "Autenticação necessária")
    @ApiResponse(responseCode = "404", description = "Nenhum dado encontrado")
    @GetMapping("/me")
    public ResponseEntity getUserData(HttpServletRequest request){
        var loggedUserId = (String) request.getAttribute("userId");

        if(loggedUserId == null)
            return ResponseEntity.status(401).build();

        var response = service.getLoggedUserData(loggedUserId);

        return ResponseEntity.ok(response);
    }
}

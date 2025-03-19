package ExpensesDashboard.Adapters.Controller;

import ExpensesDashboard.Application.DTOs.LoginRequestDto;
import ExpensesDashboard.Application.DTOs.RegisterRequestDto;
import ExpensesDashboard.Application.Services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Gerenciamento de usuários")
public class AuthController {
    private final UserService _service;

    @Operation(
            summary = "Autenticação do usuário",
            description = "Executa a autenticação para o usuário"
    )
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDto body){
        var request = _service.login(body);

        if(request.isEmpty())
            return  ResponseEntity.badRequest().build();

        return ResponseEntity.ok(request);
    }

    @Operation(
            summary = "Cadastro de usuários",
            description = "Executa o cadastro de um usuário"
    )
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDto body){
        var request = _service.create(body);
        if(!request)
            return  ResponseEntity.badRequest().build();

        return ResponseEntity.ok("Registered successfully");
    }

}

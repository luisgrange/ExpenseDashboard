package ExpensesDashboard.Adapters.Controller;

import ExpensesDashboard.Application.DTOs.LoginRequestDto;
import ExpensesDashboard.Application.DTOs.LoginResponseDto;
import ExpensesDashboard.Application.DTOs.RegisterRequestDto;
import ExpensesDashboard.Application.DTOs.RegisterResponseDto;
import ExpensesDashboard.Infra.Data.UserRepository;
import ExpensesDashboard.Domain.Entities.User;
import ExpensesDashboard.Infra.Config.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository _repository;
    private final PasswordEncoder _passwordEncoder;
    private final TokenService _tokenService;

    @GetMapping
    public ResponseEntity getLogin(){
        List<User> user = _repository.findAll();

        if(user.isEmpty()){
            return ResponseEntity.badRequest().body("No tips found");
        }

        return  ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDto body){
        User user = _repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));

        if(_passwordEncoder.matches(body.password(), user.getPassword())){
            String token = _tokenService.GenerateToken(user);
            return ResponseEntity.ok(new LoginResponseDto(user.getName(), token));
        }

        return  ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDto body){
        Optional<User> user = _repository.findByEmail(body.email());
        if(user.isEmpty()){
            User newUser = new User();
            newUser.setPassword(_passwordEncoder.encode(body.password()));
            newUser.setEmail(body.email());
            newUser.setName(body.name());
            newUser.setRole("User");
            newUser.setSalary(body.salary());

            _repository.save(newUser);

            String token = _tokenService.GenerateToken(newUser);
            return ResponseEntity.ok(new RegisterResponseDto(newUser.getName(), token));
        }

        return  ResponseEntity.badRequest().build();
    }

}

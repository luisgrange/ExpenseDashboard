package ExpensesDashboard.Application.Services;

import ExpensesDashboard.Application.DTOs.LoginRequestDto;
import ExpensesDashboard.Application.DTOs.RegisterRequestDto;
import ExpensesDashboard.Domain.Entities.User;
import ExpensesDashboard.Infra.Config.TokenService;
import ExpensesDashboard.Infra.Data.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository _repository;
    private final PasswordEncoder _passwordEncoder;
    private final TokenService _tokenService;


    public boolean create(RegisterRequestDto body){
        Optional<User> user = _repository.findByEmail(body.email());

        if(user.isEmpty()){
            User newUser = new User();
            newUser.setPassword(_passwordEncoder.encode(body.password()));
            newUser.setEmail(body.email());
            newUser.setName(body.name());
            newUser.setRole("User");
            newUser.setSalary(body.salary());

            _repository.save(newUser);

            return true;
        }

        return false;
    }

    public String login(LoginRequestDto body){
        User user = _repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));

        if(_passwordEncoder.matches(body.password(), user.getPassword())){
            String token = _tokenService.GenerateToken(user);
            return token;
        }

        return "";
    }
}

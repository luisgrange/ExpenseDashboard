package ExpensesDashboard.Adapters.Controller;

import ExpensesDashboard.Application.Services.DashboardService;
import ExpensesDashboard.Infra.Config.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService service;
    private final TokenService _tokenService;
    @GetMapping
    public ResponseEntity getUserData(HttpServletRequest request){
        var loggedUserId = (String) request.getAttribute("userid");

        var response = service.getLoggedUserData(loggedUserId);

        return ResponseEntity.ok(response);
    }
}

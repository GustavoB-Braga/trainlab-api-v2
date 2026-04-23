import br.com.trainlab.trainlab.dto.user.UserRequestDto;
import br.com.trainlab.trainlab.dto.user.UserResponseDto;
import br.com.trainlab.trainlab.dto.user.UserUpdateRequestDto;
import br.com.trainlab.trainlab.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    private String getAuthenticatedUserEmail() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }

    //------CREATE USER--------\\

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createUser(dto));
    }

    //------GET USER LOGADO--------\\

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMe() {
        String email = getAuthenticatedUserEmail();
        return ResponseEntity.ok(service.getMe(email));
    }

    //------UPDATE USER--------\\

    @PutMapping("/me")
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody @Valid UserUpdateRequestDto dto) {
        String email = getAuthenticatedUserEmail();
        return ResponseEntity.ok(service.updateUser(email, dto));
    }

    //------DELETE USER--------\\

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteUser() {
        String email = getAuthenticatedUserEmail();
        service.deleteUser(email);
        return ResponseEntity.noContent().build();
    }
}
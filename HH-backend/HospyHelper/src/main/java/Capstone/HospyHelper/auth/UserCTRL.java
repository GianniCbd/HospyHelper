package Capstone.HospyHelper.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserCTRL {
    @Autowired
    UserSRV userSRV;

    @GetMapping
    public Page<User> getAll(@RequestParam(defaultValue = "0") int pageNumber,
                             @RequestParam(defaultValue = "10") int pageSize,
                             @RequestParam(defaultValue = "name") String orderBy) {
        return userSRV.getAll(pageNumber, pageSize, orderBy);
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable UUID id) {
        return userSRV.findById(id);
    }

    @GetMapping("/me")
    public UserResponseDTO getCurrentUser(@AuthenticationPrincipal User authenticatedUser) {
        return new UserResponseDTO(
                authenticatedUser.getId(),
                authenticatedUser.getName(),
                authenticatedUser.getSurname(),
                authenticatedUser.getEmail(),
                authenticatedUser.getPassword(),
                authenticatedUser.getConfirmPassword()
        );
    }

    @PutMapping("me/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public User findByIdAndUpdate(@PathVariable UUID id, @RequestBody UserDTO userDTO, @AuthenticationPrincipal User currentAuthenticatedUser) {
        return userSRV.findByIdAndUpdate(id, userDTO, currentAuthenticatedUser);
    }

    @DeleteMapping("me/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthorById(@PathVariable UUID id, @AuthenticationPrincipal User currentAuthenticatedUser) {
        userSRV.deleteById(id, currentAuthenticatedUser);
    }


}

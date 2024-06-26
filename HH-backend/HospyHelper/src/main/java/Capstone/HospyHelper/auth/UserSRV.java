package Capstone.HospyHelper.auth;

import Capstone.HospyHelper.exceptions.BadRequestException;
import Capstone.HospyHelper.exceptions.NotFoundException;
import Capstone.HospyHelper.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class UserSRV {
    @Autowired
    UserDAO userDAO;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Page<User> getAll(int pageNumber, int pageSize, String orderBy) {
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));
        return userDAO.findAll(pageable);
    }


    public User findById(UUID id) {
        return userDAO.findById(UUID.fromString(String.valueOf(id))).orElseThrow(() -> new NotFoundException(String.valueOf(id)));
    }

    public UserResponseDTO save(UserDTO userDTO) throws IOException {
        if (userDAO.existsByEmail(userDTO.email())) {
            throw new BadRequestException("email already exist");
        }
        User user = new User(
                userDTO.name(),
                userDTO.surname(),
                userDTO.email(),
                passwordEncoder.encode(userDTO.password()),
                passwordEncoder.encode(userDTO.confirmPassword())
        );

        User savedUser = userDAO.save(user);
        return new UserResponseDTO(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getSurname(),
                savedUser.getEmail(),
                savedUser.getPassword(),
                savedUser.getConfirmPassword()
        );
    }

    public User findByEmail(String email) {
        return userDAO.findByEmail(email).orElseThrow(() -> new NotFoundException(email ));
    }
    public User findByIdAndUpdate(UUID id, UserDTO userDTO,User user){
        User found= findById(UUID.fromString(String.valueOf(id)));
        if (!user.getId().equals(found.getId())) throw new UnauthorizedException("User with wrong id");
        found.setName(userDTO.name());
        found.setSurname(userDTO.surname());
        found.setEmail(userDTO.email());

        return userDAO.save(found);
    }
    public void deleteById(UUID id, User user) {
        User found = findById(id);
        if (!user.getId().equals(UUID.fromString(String.valueOf(id)))) throw new UnauthorizedException("User with wrong id");
        userDAO.delete(found);
    }
}
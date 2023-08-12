package book.shop.bookstore.service;

import book.shop.bookstore.dto.user.UserRegistrationRequestDto;
import book.shop.bookstore.dto.user.UserResponseDto;
import book.shop.bookstore.exception.RegistrationException;
import book.shop.bookstore.mapper.UserMapper;
import book.shop.bookstore.model.Role;
import book.shop.bookstore.model.RoleName;
import book.shop.bookstore.model.User;
import book.shop.bookstore.repository.user.UserRepository;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto) {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException("this email is already in use");
        }
        User user = userMapper.toModel(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        Role userRole = roleService.getRoleByRoleName(RoleName.ROLE_USER);
        user.setRoles(new HashSet<>(Set.of(userRole)));
        return userMapper.toDto(userRepository.save(user));
    }
}

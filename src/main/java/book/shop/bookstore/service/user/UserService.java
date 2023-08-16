package book.shop.bookstore.service.user;

import book.shop.bookstore.dto.user.UserRegistrationRequestDto;
import book.shop.bookstore.dto.user.UserResponseDto;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto);
}

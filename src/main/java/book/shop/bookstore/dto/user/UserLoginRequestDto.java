package book.shop.bookstore.dto.user;

import book.shop.bookstore.lib.PasswordValidator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserLoginRequestDto(
        @Size(min = 8, max = 25)
        @Email
        String email,
        @PasswordValidator
        String password
) {
}

package book.shop.bookstore.dto.user;

import book.shop.bookstore.lib.FieldMatch;
import book.shop.bookstore.lib.PasswordValidator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@FieldMatch(
        field = "password",
        fieldMatch = "repeatPassword",
        message = "Passwords do not match!"
)
public class UserRegistrationRequestDto {
    @Email
    @Size(min = 8, max = 50)
    private String email;
    @NotNull
    @Size(min = 8, max = 20)
    @PasswordValidator
    private String password;
    private String repeatPassword;
    @NotNull
    @Size(min = 2, max = 35)
    private String firstName;
    @Size(min = 2, max = 35)
    private String lastName;
    @NotNull
    @Size(min = 5, max = 90)
    private String shippingAddress;
}

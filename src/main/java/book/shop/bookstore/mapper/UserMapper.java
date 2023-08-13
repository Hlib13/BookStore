package book.shop.bookstore.mapper;

import book.shop.bookstore.config.MapperConfig;
import book.shop.bookstore.dto.user.UserRegistrationRequestDto;
import book.shop.bookstore.dto.user.UserResponseDto;
import book.shop.bookstore.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    User toModel(UserRegistrationRequestDto requestDto);
}

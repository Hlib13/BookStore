package book.shop.bookstore.mapper;

import book.shop.bookstore.config.MapperConfig;
import book.shop.bookstore.dto.shoppingcart.cart.CartItemResponseDto;
import book.shop.bookstore.dto.shoppingcart.cart.CreatCartItemRequestDto;
import book.shop.bookstore.dto.shoppingcart.cart.UpdateCartItemDto;
import book.shop.bookstore.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {

    CartItemResponseDto toDto(CartItem cartItem);

    @Mapping(target = "id", ignore = true)
    CartItem toModel(CreatCartItemRequestDto cartItemRequestDto);

    @Mapping(target = "id", ignore = true)
    CartItem toModel(UpdateCartItemDto updateCartItemDto);
}

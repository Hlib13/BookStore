package book.shop.bookstore.dto.shoppingcart;

import book.shop.bookstore.dto.shoppingcart.cart.CartItemResponseDto;
import java.util.List;
import lombok.Data;

@Data
public class ShoppingCartDto {
    private Long id;
    private Long userId;
    private List<CartItemResponseDto> cartItems;
}

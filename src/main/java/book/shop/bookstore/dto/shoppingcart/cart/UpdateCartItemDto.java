package book.shop.bookstore.dto.shoppingcart.cart;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UpdateCartItemDto {
    private int quantity;
}

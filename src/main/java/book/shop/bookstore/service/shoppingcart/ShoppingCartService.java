package book.shop.bookstore.service.shoppingcart;

import book.shop.bookstore.dto.shoppingcart.ShoppingCartDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ShoppingCartService {
    ShoppingCartDto getByUserId(Long userId);

    List<ShoppingCartDto> getAll(Pageable pageable);
}

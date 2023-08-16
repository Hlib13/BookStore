package book.shop.bookstore.service.shoppingcart;

import book.shop.bookstore.dto.shoppingcart.ShoppingCartDto;
import book.shop.bookstore.mapper.ShoppingCartMapper;
import book.shop.bookstore.model.ShoppingCart;
import book.shop.bookstore.repository.cart.ShoppingCartRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    public ShoppingCartDto getByUserId(Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId);
        if (shoppingCart == null) {
            throw new RuntimeException("Can't found cart by user id: " + userId);
        }
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public List<ShoppingCartDto> getAll(Pageable pageable) {
        return shoppingCartRepository.findAll(pageable).stream()
                .map(shoppingCartMapper::toDto)
                .toList();
    }
}

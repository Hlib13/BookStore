package book.shop.bookstore.service.shoppingcart.cartitem;

import book.shop.bookstore.dto.shoppingcart.cart.CartItemResponseDto;
import book.shop.bookstore.dto.shoppingcart.cart.CreatCartItemRequestDto;
import book.shop.bookstore.dto.shoppingcart.cart.UpdateCartItemDto;
import book.shop.bookstore.mapper.CartItemMapper;
import book.shop.bookstore.model.CartItem;
import book.shop.bookstore.repository.cart.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;

    @Override
    public CartItemResponseDto getByBookId(Long bookId) {
        CartItem cartItem = cartItemRepository.findByBookId(bookId);
        if (cartItem == null) {
            throw new RuntimeException("Can't found cart item by bookId:" + bookId);
        }
        return cartItemMapper.toDto(cartItem);
    }

    @Override
    public CartItemResponseDto update(Long id, UpdateCartItemDto updateCartItemDto) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't found cart item by id: " + id));
        cartItem.setQuantity(updateCartItemDto.getQuantity());
        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    public CartItemResponseDto addBook(CreatCartItemRequestDto cartItemRequestDto) {
        CartItem cartItem = cartItemMapper.toModel(cartItemRequestDto);
        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    public void deleteById(Long id) {
        cartItemRepository.deleteById(id);
    }
}

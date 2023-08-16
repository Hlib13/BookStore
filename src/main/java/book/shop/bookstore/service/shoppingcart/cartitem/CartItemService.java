package book.shop.bookstore.service.shoppingcart.cartitem;

import book.shop.bookstore.dto.shoppingcart.cart.CartItemResponseDto;
import book.shop.bookstore.dto.shoppingcart.cart.CreatCartItemRequestDto;
import book.shop.bookstore.dto.shoppingcart.cart.UpdateCartItemDto;

public interface CartItemService {
    CartItemResponseDto getByBookId(Long bookId);

    CartItemResponseDto update(Long id, UpdateCartItemDto updateCartItemDto);

    CartItemResponseDto addBook(CreatCartItemRequestDto cartItemRequestDto);

    void deleteById(Long id);
}

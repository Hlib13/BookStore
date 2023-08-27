package book.shop.bookstore.controller;

import book.shop.bookstore.dto.shoppingcart.cart.CartItemResponseDto;
import book.shop.bookstore.dto.shoppingcart.cart.CreatCartItemRequestDto;
import book.shop.bookstore.dto.shoppingcart.cart.UpdateCartItemDto;
import book.shop.bookstore.service.shoppingcart.cartitem.CartItemService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/cart/cart-items")
public class CartItemController {
    private final CartItemService cartItemService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{bookId}")
    @Operation(summary = "Get cartItem by book id",
            description = "Get available cart item by bookId")
    public CartItemResponseDto getByBookId(@PathVariable Long bookId) {
        return cartItemService.getByBookId(bookId);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete cart-item by id",
            description = "Soft delete of available cart-item by id")
    public void deleteById(@PathVariable Long id) {
        cartItemService.deleteById(id);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/{id}")
    @Operation(summary = "Update cart-item by id",
            description = "Update available cart-item by id")
    public CartItemResponseDto update(@PathVariable Long id,
                                      @RequestBody @Valid UpdateCartItemDto requestDto) {
        return cartItemService.update(id, requestDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    @Operation(summary = "Add book to cartItem", description = "Add valid book to cartItem")
    public CartItemResponseDto addBook(@RequestBody @Valid CreatCartItemRequestDto requestDto) {
        return cartItemService.addBook(requestDto);
    }
}

package book.shop.bookstore.controller;

import book.shop.bookstore.dto.shoppingcart.ShoppingCartDto;
import book.shop.bookstore.service.shoppingcart.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    @Operation(summary = "Get all categories",
            description = "Get a list of all available carts")
    public List<ShoppingCartDto> findAll(@ParameterObject Pageable pageable) {
        return shoppingCartService.getAll(pageable);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{userId}")
    @Operation(summary = "Get shopping cart by user id",
            description = "Get available cart by userId")
    public ShoppingCartDto getByUserId(@PathVariable Long userId) {
        return shoppingCartService.getByUserId(userId);
    }
}

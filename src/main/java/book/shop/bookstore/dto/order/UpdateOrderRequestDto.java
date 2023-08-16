package book.shop.bookstore.dto.order;

import book.shop.bookstore.model.Order;
import lombok.Data;

@Data
public class UpdateOrderRequestDto {
    private Order.Status status;
}

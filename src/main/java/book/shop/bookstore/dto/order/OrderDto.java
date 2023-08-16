package book.shop.bookstore.dto.order;

import book.shop.bookstore.dto.order.orderitem.OrderItemDto;
import book.shop.bookstore.model.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public class OrderDto {
    private Long id;
    private Long userId;
    private Set<OrderItemDto> orderItems;
    private LocalDate orderDate;
    private BigDecimal total;
    private Order.Status status;
}

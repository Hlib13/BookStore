package book.shop.bookstore.service.order;

import book.shop.bookstore.dto.order.CreateOrderRequestDto;
import book.shop.bookstore.dto.order.OrderDto;
import book.shop.bookstore.dto.order.UpdateOrderRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderDto update(Long id, UpdateOrderRequestDto requestDto);

    OrderDto addOrder(CreateOrderRequestDto createOrderRequestDto);

    OrderDto getByUserId(Long userId);

    OrderDto findById(Long id);

    List<OrderDto> getAll(Pageable pageable);

    OrderDto getByOrderIdAndOrderItemId(Long orderId, Long orderItemsId);
}

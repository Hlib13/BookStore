package book.shop.bookstore.service.order.orderitem;

import book.shop.bookstore.dto.order.orderitem.OrderItemDto;

public interface OrderItemService {
    OrderItemDto getByBookId(Long bookId);

    OrderItemDto findById(Long id);

    void deleteById(Long id);
}

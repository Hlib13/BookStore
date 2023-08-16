package book.shop.bookstore.repository.order;

import book.shop.bookstore.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    OrderItem findByBookId(Long bookId);
}

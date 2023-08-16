package book.shop.bookstore.service.order.orderitem;

import book.shop.bookstore.dto.order.orderitem.OrderItemDto;
import book.shop.bookstore.exception.EntityNotFoundException;
import book.shop.bookstore.mapper.OrderItemMapper;
import book.shop.bookstore.model.OrderItem;
import book.shop.bookstore.repository.order.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public OrderItemDto getByBookId(Long bookId) {
        OrderItem orderItem = orderItemRepository.findByBookId(bookId);
        if (orderItem == null) {
            throw new RuntimeException("Can't found order by book id: " + bookId);
        }
        return orderItemMapper.toDto(orderItem);
    }

    @Override
    public OrderItemDto findById(Long id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't found order by id: " + id));
        return orderItemMapper.toDto(orderItem);
    }

    @Override
    public void deleteById(Long id) {
        orderItemRepository.deleteById(id);
    }
}

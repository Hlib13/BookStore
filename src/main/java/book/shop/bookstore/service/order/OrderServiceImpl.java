package book.shop.bookstore.service.order;

import book.shop.bookstore.dto.order.CreateOrderRequestDto;
import book.shop.bookstore.dto.order.OrderDto;
import book.shop.bookstore.dto.order.UpdateOrderRequestDto;
import book.shop.bookstore.exception.EntityNotFoundException;
import book.shop.bookstore.mapper.OrderMapper;
import book.shop.bookstore.model.Order;
import book.shop.bookstore.repository.order.OrderRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderDto update(Long id, UpdateOrderRequestDto requestDto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't found order by id: " + id));
        order.setStatus(requestDto.getStatus());
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public OrderDto addOrder(CreateOrderRequestDto createOrderRequestDto) {
        Order order = orderMapper.toModel(createOrderRequestDto);
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public OrderDto getByUserId(Long userId) {
        Order order = orderRepository.findByUserId(userId);
        if (order == null) {
            throw new RuntimeException("Can't found order by user id: " + userId);
        }
        return orderMapper.toDto(order);
    }

    @Override
    public OrderDto findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't found order by id: " + id));
        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderDto> getAll(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public OrderDto getByOrderIdAndOrderItemId(Long orderId, Long orderItemsId) {
        Order order = orderRepository.findByOrderIdAndOrderItemsId(orderId, orderItemsId)
                .orElseThrow(() -> new RuntimeException("Can't found OrderId and ItemsId"));
        return orderMapper.toDto(order);
    }
}

package book.shop.bookstore.mapper;

import book.shop.bookstore.config.MapperConfig;
import book.shop.bookstore.dto.order.orderitem.OrderItemDto;
import book.shop.bookstore.model.OrderItem;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    OrderItemDto toDto(OrderItem orderItem);
}

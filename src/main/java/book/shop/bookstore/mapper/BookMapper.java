package book.shop.bookstore.mapper;

import book.shop.bookstore.config.MapperConfig;
import book.shop.bookstore.dto.request.CreateBookRequestDto;
import book.shop.bookstore.dto.response.BookDto;
import book.shop.bookstore.model.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    Book toModel(CreateBookRequestDto createBookRequestDto);

    BookDto toDto(Book book);
}

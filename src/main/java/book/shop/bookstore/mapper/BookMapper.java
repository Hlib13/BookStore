package book.shop.bookstore.mapper;

import book.shop.bookstore.config.MapperConfig;
import book.shop.bookstore.dto.book.BookDto;
import book.shop.bookstore.dto.book.CreateBookRequestDto;
import book.shop.bookstore.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    @Mapping(target = "id", ignore = true)
    Book toModel(CreateBookRequestDto createBookRequestDto);

    BookDto toDto(Book book);
}

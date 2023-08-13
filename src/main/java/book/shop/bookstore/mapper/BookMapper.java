package book.shop.bookstore.mapper;

import book.shop.bookstore.config.MapperConfig;
import book.shop.bookstore.dto.book.BookDto;
import book.shop.bookstore.dto.book.BookDtoWithoutCategoryIds;
import book.shop.bookstore.dto.book.CreateBookRequestDto;
import book.shop.bookstore.exception.EntityNotFoundException;
import book.shop.bookstore.model.Book;
import book.shop.bookstore.model.Category;
import book.shop.bookstore.repository.book.BookRepository;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    @Mapping(target = "id", ignore = true)
    Book toModel(CreateBookRequestDto createBookRequestDto);

    BookDto toDto(Book book);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        bookDto.setCategoryIds(book.getCategories()
                .stream()
                .map(Category::getId)
                .collect(Collectors.toSet()));
    }

    @Named("bookFromId")
    default Book bookFromId(Long id, BookRepository bookRepository) {
        return bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't found book by id: " + id));
    }
}

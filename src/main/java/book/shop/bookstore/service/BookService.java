package book.shop.bookstore.service;

import book.shop.bookstore.dto.book.BookDto;
import book.shop.bookstore.dto.book.BookSearchParameters;
import book.shop.bookstore.dto.book.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto product);

    public BookDto getBookById(Long id);

    List<BookDto> findAll(Pageable pageable);

    BookDto update(Long id, CreateBookRequestDto createBookRequestDto);

    public List<BookDto> search(BookSearchParameters params);

    void delete(Long id);
}

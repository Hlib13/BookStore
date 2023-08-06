package book.shop.bookstore.service;

import book.shop.bookstore.dto.request.CreateBookRequestDto;
import book.shop.bookstore.dto.response.BookDto;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto product);

    public BookDto getBookById(Long id);

    List<BookDto> findAll();
}

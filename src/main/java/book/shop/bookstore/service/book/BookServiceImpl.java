package book.shop.bookstore.service.book;

import book.shop.bookstore.dto.book.BookDto;
import book.shop.bookstore.dto.book.BookSearchParameters;
import book.shop.bookstore.dto.book.CreateBookRequestDto;
import book.shop.bookstore.exception.EntityNotFoundException;
import book.shop.bookstore.mapper.BookMapper;
import book.shop.bookstore.model.Book;
import book.shop.bookstore.repository.book.BookRepository;
import book.shop.bookstore.repository.book.BookSpecificationBuilder;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public BookDto save(CreateBookRequestDto book) {
        return bookMapper.toDto(bookRepository.save(bookMapper.toModel(book)));
    }

    @Override
    public BookDto getBookById(Long id) {
        return bookMapper.toDto(bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Could not found book with id :" + id)));
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto update(Long id, CreateBookRequestDto createBookRequestDto) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Can't found book with id:" + id);
        }
        Book book = bookMapper.toModel(createBookRequestDto);
        book.setId(id);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> search(BookSearchParameters params) {
        Specification<Book> bookSpecification = bookSpecificationBuilder.build(params);
        return bookRepository.findAll(bookSpecification)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Can't delete book with id:" + id);
        }
        bookRepository.deleteById(id);
    }
}

package book.shop.bookstore.controller;

import book.shop.bookstore.dto.BookSearchParameters;
import book.shop.bookstore.dto.request.CreateBookRequestDto;
import book.shop.bookstore.dto.response.BookDto;
import book.shop.bookstore.service.BookService;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Books", description = "Endpoints for managing books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by id", description = "Founding book by id")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new book", description = "Creating a new book")
    public BookDto createBook(@RequestBody @Valid CreateBookRequestDto requestDto) {
        return bookService.save(requestDto);
    }

    @GetMapping
    @Operation(summary = "Get all books", description = "Get all books")
    public List<BookDto> getAllBooks(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update book", description = "Change info of book")
    public BookDto updateBook(@PathVariable Long id, @RequestBody @Valid CreateBookRequestDto requestDto) {
        return bookService.update(id, requestDto);
    }

    @GetMapping("/search")
    @Operation(summary = "Search by parameters", description = "Searching by parameters")
    public List<BookDto> search(BookSearchParameters params) {
        return bookService.search(params);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete book", description = "Delete book by id")
    public void deleteBook(@PathVariable Long id) {
        bookService.delete(id);
    }


}

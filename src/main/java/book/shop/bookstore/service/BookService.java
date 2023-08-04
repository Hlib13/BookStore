package book.shop.bookstore.service;

import book.shop.bookstore.model.Book;
import java.util.List;

public interface BookService {
    Book save(Book product);

    List<Book> findAll();
}

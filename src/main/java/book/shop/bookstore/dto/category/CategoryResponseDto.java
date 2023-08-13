package book.shop.bookstore.dto.category;

import book.shop.bookstore.model.Book;
import java.util.Set;
import lombok.Data;

@Data
public class CategoryResponseDto {
    private Long id;
    private String name;
    private String description;
    private Set<Book> books;
}

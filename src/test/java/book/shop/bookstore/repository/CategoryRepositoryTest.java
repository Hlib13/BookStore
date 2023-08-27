package book.shop.bookstore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import book.shop.bookstore.model.Book;
import book.shop.bookstore.repository.category.CategoryRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Sql(scripts = "classpath:database/book/add-books-and-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/delete-all-book&category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @DisplayName("Get all books by with two books in category")
    void getBooksByCategoriesIds_Ok() {
        List<Book> actual = categoryRepository.getBooksByCategoryId(1L);
        assertNotNull(actual);
        assertEquals(2, actual.size());
    }

    @Sql(scripts = "classpath:database/book/add-books-and-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/delete-all-book&category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @DisplayName("Get all books by with one book in category")
    void getBooksByCategoriesId_Ok() {
        List<Book> actual = categoryRepository.getBooksByCategoryId(2L);
        assertNotNull(actual);
        assertEquals(1, actual.size());
    }

    @Sql(scripts = "classpath:database/book/add-books-and-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/delete-all-book&category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @DisplayName("Get empty list of books by non-existent category")
    void getBooksByCategoriesId_NotOk() {
        List<Book> actual = categoryRepository.getBooksByCategoryId(10L);
        assertNotNull(actual);
        assertEquals(0, actual.size());
    }
}

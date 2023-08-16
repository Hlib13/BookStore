package book.shop.bookstore.repository.category;

import book.shop.bookstore.model.Book;
import book.shop.bookstore.model.Category;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT b FROM Book b JOIN b.categories c WHERE c.id = :categoryId")
    Set<Book> getBooksByCategoryId(@Param("categoryId") Long categoryId);
}

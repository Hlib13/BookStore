package book.shop.bookstore.repository.category;

import book.shop.bookstore.model.Book;
import book.shop.bookstore.model.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT b FROM Book b JOIN b.categories c WHERE c.id = :categoryId")
    List<Book> getBooksByCategoryId(@Param("categoryId") Long categoryId);

    @EntityGraph(attributePaths = "books")
    Optional<Category> findById(Long id);
}

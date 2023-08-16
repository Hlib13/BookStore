package book.shop.bookstore.service.category;

import book.shop.bookstore.dto.category.CategoryDto;
import book.shop.bookstore.dto.category.CreateCategoryRequestDto;
import book.shop.bookstore.model.Book;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    List<CategoryDto> findAll(Pageable pageable);

    CategoryDto getById(Long id);

    CategoryDto save(CreateCategoryRequestDto categoryDto);

    CategoryDto update(Long id, CreateCategoryRequestDto categoryDto);

    void deleteById(Long id);

    Set<Book> getBooksByCategoryId(Long categoryId);
}

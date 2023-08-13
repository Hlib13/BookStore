package book.shop.bookstore.service;

import book.shop.bookstore.dto.category.CategoryResponseDto;
import book.shop.bookstore.dto.category.CreateCategoryRequestDto;
import book.shop.bookstore.model.Book;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    List<CategoryResponseDto> findAll(Pageable pageable);

    CategoryResponseDto getById(Long id);

    CategoryResponseDto save(CreateCategoryRequestDto categoryDto);

    CategoryResponseDto update(Long id, CreateCategoryRequestDto categoryDto);

    void deleteById(Long id);

    List<Book> getBooksByCategoryId(Long id);
}

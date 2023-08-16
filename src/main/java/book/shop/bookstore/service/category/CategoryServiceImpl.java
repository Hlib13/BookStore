package book.shop.bookstore.service.category;

import book.shop.bookstore.dto.category.CategoryDto;
import book.shop.bookstore.dto.category.CreateCategoryRequestDto;
import book.shop.bookstore.exception.EntityNotFoundException;
import book.shop.bookstore.mapper.CategoryMapper;
import book.shop.bookstore.model.Book;
import book.shop.bookstore.model.Category;
import book.shop.bookstore.repository.category.CategoryRepository;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).stream().map(categoryMapper::toDto).toList();
    }

    @Override
    public CategoryDto getById(Long id) {
        return categoryMapper.toDto(categoryRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't found category by id: " + id)));
    }

    @Override
    public CategoryDto save(CreateCategoryRequestDto categoryDto) {
        return categoryMapper.toDto(categoryRepository.save(categoryMapper.toModel(categoryDto)));
    }

    @Override
    public CategoryDto update(Long id, CreateCategoryRequestDto categoryDto) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Can't found category with id: " + id);
        }
        Category category = categoryMapper.toModel(categoryDto);
        category.setId(id);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public void deleteById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Can't delete category with id: " + id);
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public Set<Book> getBooksByCategoryId(Long id) {
        return categoryRepository.getBooksByCategoryId(id);
    }
}

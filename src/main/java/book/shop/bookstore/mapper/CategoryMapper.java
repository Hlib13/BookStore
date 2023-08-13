package book.shop.bookstore.mapper;

import book.shop.bookstore.config.MapperConfig;
import book.shop.bookstore.dto.category.CategoryResponseDto;
import book.shop.bookstore.dto.category.CreateCategoryRequestDto;
import book.shop.bookstore.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryResponseDto toDto(Category category);

    @Mapping(target = "id", ignore = true)
    Category toModel(CreateCategoryRequestDto categoryDto);
}

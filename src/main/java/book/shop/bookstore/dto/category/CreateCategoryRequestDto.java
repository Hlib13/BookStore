package book.shop.bookstore.dto.category;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateCategoryRequestDto {
    @NotNull
    @Size(min = 2, max = 60)
    private String name;
    @NotNull
    private String description;

}

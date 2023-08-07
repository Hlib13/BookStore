package book.shop.bookstore.repository;

import book.shop.bookstore.model.Book;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationProvider<T> {
    String getKey();
    Specification<T> getSpecification(String[] params);
}

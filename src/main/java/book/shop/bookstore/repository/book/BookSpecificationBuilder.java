package book.shop.bookstore.repository.book;

import book.shop.bookstore.dto.BookSearchParameters;
import book.shop.bookstore.model.Book;
import book.shop.bookstore.repository.SpecificationBuilder;
import book.shop.bookstore.repository.SpecificationProviderManager;
import book.shop.bookstore.repository.book.AuthorSpecificationProvider;
import book.shop.bookstore.repository.book.TitleSpecificationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    public static final String AUTHOR = "author";
    public static final String TITLE = "title";
    private final SpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParameters searchParameters) {
        Specification<Book> specification = Specification.where(null);
        if (searchParameters.titles() != null && searchParameters.titles().length > 0) {
            specification = specification.and(bookSpecificationProviderManager
                    .getSpecificationProvider(TITLE)
                    .getSpecification(searchParameters.titles()));
        }
        if (searchParameters.authors() != null && searchParameters.authors().length > 0) {
            specification = specification.and(bookSpecificationProviderManager
                    .getSpecificationProvider(AUTHOR)
                    .getSpecification(searchParameters.authors()));
        }
        return specification;
    }
}

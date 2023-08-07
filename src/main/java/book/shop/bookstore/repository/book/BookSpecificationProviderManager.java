package book.shop.bookstore.repository.book;

import book.shop.bookstore.model.Book;
import book.shop.bookstore.repository.SpecificationProvider;
import book.shop.bookstore.repository.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {

    private List<SpecificationProvider<Book>> bookSpecificationProviders;
    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String key) {
        return bookSpecificationProviders.stream()
                .filter(b -> b.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new  RuntimeException("Can't find specification provider for key " + key));
    }
}

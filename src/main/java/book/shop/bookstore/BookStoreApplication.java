package book.shop.bookstore;

import book.shop.bookstore.model.Book;
import book.shop.bookstore.service.BookService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookStoreApplication {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(BookStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book titenik = new Book();
            titenik.setTitle("Titenik");
            titenik.setAuthor("Someone");
            titenik.setIsbn("134");
            titenik.setPrice(BigDecimal.valueOf(999));

            bookService.save(titenik);
            System.out.println(bookService.findAll());
        };
    }

}

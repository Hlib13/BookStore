package book.shop.bookstore.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import book.shop.bookstore.dto.book.BookDto;
import book.shop.bookstore.dto.book.CreateBookRequestDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {
    protected static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    @Sql(scripts = "classpath:database/book/delete-all-book&category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    static void beforeAll(@Autowired WebApplicationContext context) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser
    @Sql(scripts = "classpath:database/book/add-books-and-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/delete-all-book&category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Get all books(2)")
    public void getAllBooks_Ok() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andReturn();
        List<BookDto> actual = objectMapper.readValue(mvcResult
                .getResponse()
                .getContentAsString(), new TypeReference<>() {});
        assertNotNull(actual);
        assertEquals(2, actual.size());
    }

    @Test
    @WithMockUser
    @DisplayName("Get book by id 1")
    @Sql(scripts = "classpath:database/book/add-books-and-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/delete-all-book&category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getBookById_Ok() throws Exception {
        MvcResult result = mockMvc.perform(get("/books/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Long expectedId = 1L;
        BookDto actual = objectMapper.readValue(result
                .getResponse().getContentAsString(), BookDto.class);
        assertNotNull(actual);
        assertEquals(expectedId, actual.getId());
    }

    @Test
    @WithMockUser
    @DisplayName("Get book by wrong id 999, expected not found status")
    public void getBookById999_NotOk() throws Exception {
        mockMvc.perform(get("/books/{bookId}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Create a book")
    @Sql(scripts = "classpath:database/book/add-books-and-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/delete-all-book&category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void createBook_Ok() throws Exception {
        CreateBookRequestDto createBookRequestDto = createBook();
        String request = objectMapper.writeValueAsString(createBookRequestDto);
        MvcResult mvcResult = mockMvc.perform(
                        post("/books")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        BookDto expected = createBookResponseDto(createBookRequestDto);
        BookDto actual = objectMapper.readValue(mvcResult.getResponse()
                .getContentAsString(), BookDto.class);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(scripts = "classpath:database/book/add-books-and-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/delete-all-book&category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Update book with id 1")
    public void updateBook_Ok() throws Exception {
        long bookId = 1;
        CreateBookRequestDto updateBookRequestDto = createBook().setIsbn("12345");
        updateBookRequestDto.setTitle("Updated book");
        String request = objectMapper.writeValueAsString(updateBookRequestDto);
        MvcResult mvcResult = mockMvc.perform(
                        put("/books/{id}", bookId)
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        BookDto expected = createBookResponseDto(createBook())
                .setId(bookId)
                .setIsbn("12345")
                .setTitle("Updated book");
        BookDto actual = objectMapper.readValue(mvcResult.getResponse()
                .getContentAsString(), BookDto.class);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Update book with wrong id 999, expected not found status")
    @Sql(scripts = "classpath:database/book/delete-all-book&category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateBook_NotOk() throws Exception {
        long bookId = 999;
        CreateBookRequestDto updateBookRequestDto = createBook().setIsbn("123456789990");
        updateBookRequestDto.setTitle("Updated book");
        String request = objectMapper.writeValueAsString(updateBookRequestDto);
        mockMvc.perform(
                        put("/books/{id}", bookId)
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser
    @Sql(scripts = "classpath:database/book/add-books-and-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/delete-all-book&category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Search book by author Someone, expected 1 book")
    public void searchBookByAuthor_Ok() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        get("/books/search")
                                .param("authors", "Someone"))
                .andExpect(status().isOk())
                .andReturn();
        List<BookDto> actual = objectMapper.readValue(mvcResult
                .getResponse().getContentAsString(), new TypeReference<>() {});
        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals("Someone", actual.get(0).getAuthor());
    }

    @Test
    @WithMockUser
    @Sql(scripts = "classpath:database/book/add-books-and-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/delete-all-book&category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Search book by title, expected 1 book")
    public void searchBookByTitle_Ok() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        get("/books/search")
                                .param("titles", "Avatar"))
                .andExpect(status().isOk())
                .andReturn();
        List<BookDto> actual = objectMapper.readValue(mvcResult
                .getResponse().getContentAsString(), new TypeReference<>() {});
        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals("Avatar", actual.get(0).getTitle());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(scripts = "classpath:database/book/add-books-and-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/delete-all-book&category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Delete book by id 1")
    public void deleteBookById1_Ok() throws Exception {
        mockMvc.perform(
                        delete("/books/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Delete book by wrong id 999, expected status not found")
    public void deleteBookByWrongId() throws Exception {
        mockMvc.perform(
                        delete("/books/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    private static CreateBookRequestDto createBook() {
        return new CreateBookRequestDto()
                .setDescription("new world")
                .setCoverImage("avatar-new-world.jpg")
                .setAuthor("Someone")
                .setIsbn("1234578")
                .setTitle("Avatar")
                .setPrice(new BigDecimal("19.99"))
                .setCategoryIds(List.of(1L));
    }

    private static BookDto createBookResponseDto(CreateBookRequestDto dto) {
        return new BookDto()
                .setIsbn(dto.getIsbn())
                .setCategoryIds(dto.getCategoryIds())
                .setAuthor(dto.getAuthor())
                .setDescription(dto.getDescription())
                .setPrice(dto.getPrice())
                .setCoverImage(dto.getCoverImage())
                .setTitle(dto.getTitle())
                .setId(3L);
    }
}

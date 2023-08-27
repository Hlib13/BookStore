package book.shop.bookstore.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import book.shop.bookstore.dto.book.BookDtoWithoutCategoryIds;
import book.shop.bookstore.dto.category.CategoryDto;
import book.shop.bookstore.dto.category.CreateCategoryRequestDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class CategoryControllerTest {
    protected static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
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
    @DisplayName("Get all categories, expected size 2")
    void getAllCategories_Ok() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        get("/categories"))
                .andExpect(status().isOk())
                .andReturn();
        List<CategoryDto> actual = objectMapper.readValue(mvcResult
                .getResponse().getContentAsString(), new TypeReference<>() {});
        assertNotNull(actual);
        assertEquals(2, actual.size());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(scripts = "classpath:database/book/add-books-and-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/delete-all-book&category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Creat category")
    void creatCategory_Ok() throws Exception {
        CreateCategoryRequestDto categoryRequestDto = new CreateCategoryRequestDto();
        categoryRequestDto.setName("Category name");
        categoryRequestDto.setDescription("Category description");
        String request = objectMapper.writeValueAsString(categoryRequestDto);
        MvcResult mvcResult = mockMvc.perform(post("/categories")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        CategoryDto expected = new CategoryDto()
                .setId(3L)
                .setName(categoryRequestDto.getName())
                .setDescription(categoryRequestDto.getDescription());
        CategoryDto actual = objectMapper
                .readValue(mvcResult.getResponse()
                        .getContentAsString(), CategoryDto.class);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(scripts = "classpath:database/book/add-books-and-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/delete-all-book&category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Update category")
    void updateCategory_Ok() throws Exception {
        Long categoryId = 1L;
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto()
                .setName("Category name")
                .setDescription("Category description");
        String request = objectMapper.writeValueAsString(requestDto);
        MvcResult mvcResult = mockMvc.perform(put("/categories/{id}",categoryId)
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        CategoryDto expected = new CategoryDto()
                .setId(categoryId)
                .setName(requestDto.getName())
                .setDescription(requestDto.getDescription());
        CategoryDto actual = objectMapper.readValue(mvcResult.getResponse()
                .getContentAsString(), CategoryDto.class);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(scripts = "classpath:database/book/add-books-and-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/delete-all-book&category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Update category with wrong id 999, expected status not found")
    void updateCategory_NotOk() throws Exception {
        Long categoryId = 999L;
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto()
                .setName("Category name")
                .setDescription("Category description");
        String request = objectMapper.writeValueAsString(requestDto);
        mockMvc.perform(put("/categories/{id}", categoryId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    @Sql(scripts = "classpath:database/book/add-books-and-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/delete-all-book&category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Get category by id")
    void getCategoryById_Ok() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/categories/{id}", 1L))
                .andExpect(status().isOk())
                .andReturn();
        Long expectedId = 1L;
        CategoryDto actual = objectMapper.readValue(mvcResult
                .getResponse()
                .getContentAsString(), CategoryDto.class);
        assertNotNull(actual);
        assertEquals(expectedId, actual.getId());
    }

    @Test
    @WithMockUser
    @Sql(scripts = "classpath:database/book/add-books-and-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/delete-all-book&category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Get category by wrong id")
    void getCategoryById_NotOk() throws Exception {
        mockMvc.perform(get("/categories/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    @Sql(scripts = "classpath:database/book/add-books-and-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/delete-all-book&category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Get books by category id 1, expected size 2")
    void getBookByCategoryId_Ok() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/categories/{id}/books", 1L))
                .andExpect(status().isOk())
                .andReturn();
        List<BookDtoWithoutCategoryIds> actual = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});
        Long expectedSize = 2L;
        assertNotNull(actual);
        assertEquals(expectedSize, actual.size());
    }

    @Test
    @WithMockUser
    @Sql(scripts = "classpath:database/book/add-books-and-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/delete-all-book&category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Get books by category id 999 exception")
    void getBookByCategoryId_NotOk() throws Exception {
        mockMvc.perform(get("/categories/{id}/books", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(scripts = "classpath:database/book/add-books-and-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/delete-all-book&category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Delete category")
    void deleteCategory_Ok() throws Exception {
        mockMvc.perform(delete("/categories/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(scripts = "classpath:database/book/add-books-and-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/delete-all-book&category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Delete category by wrong Id")
    void deleteCategory_NotOk() throws Exception {
        mockMvc.perform(delete("/categories/{id}", 999L))
                .andExpect(status().isNotFound());
    }
}

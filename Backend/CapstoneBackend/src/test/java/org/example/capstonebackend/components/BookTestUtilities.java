package org.example.capstonebackend.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.capstonebackend.model.Book;
import org.example.capstonebackend.model.Category;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Component
public class BookTestUtilities {
    static Book book = new Book();

    public static final Book mockBook = createMockBook();

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static Book createMockBook() {
        Book book = new Book();
        book.setId(1);
        book.setTitle("Emily's CookBook");

        return book;
    }

    public static String bookToJson (Book mockBook) {
        try {
            return objectMapper.writeValueAsString(mockBook);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void compareJsonOutputBook(ResultActions resultActions, Book mockBook) throws Exception {
        resultActions
                .andExpect(jsonPath("$.id", is(mockBook.getId())))
                .andExpect(jsonPath("$.title", is(mockBook.getTitle())));
    }

}

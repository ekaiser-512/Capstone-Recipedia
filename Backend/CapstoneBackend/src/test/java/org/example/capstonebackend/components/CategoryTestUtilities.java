package org.example.capstonebackend.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.capstonebackend.model.Category;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.core.Is.is;

@Component
public class CategoryTestUtilities {

    static Category category = new Category();

    public static final Category mockCategory = createMockCategory();

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static Category createMockCategory() {
        Category category = new Category();
        category.setId(1);
        category.setTitle("Desserts");

        return category;
    }

    public static String categoryToJson(Category mockCategory) {
        try {
            return objectMapper.writeValueAsString(mockCategory);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void compareJsonOutputCategory(ResultActions resultActions, Category mockCategory) throws Exception {
        resultActions
                .andExpect(jsonPath("$.categoryId", is(mockCategory.getId())))
                .andExpect(jsonPath("$.title", is(mockCategory.getTitle())));
    }
}

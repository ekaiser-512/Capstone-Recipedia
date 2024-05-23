package org.example.capstonebackend.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.capstonebackend.model.User;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Component
public class UserTestUtilities {

    static User user = new User();

    public static final User mockUser = createMockUser();

    public static String firstName = "Joe";

    private static String lastName = "Doe";

    private static String dateOfBirth = "02/01/1992";

    private static String email = "joey_doughy@test.com";

    private static String password = "mockPassword";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static User createMockUser() {
        User user = new User();
        user.setUserId(1);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setDateOfBirth(dateOfBirth);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }
    public static String userToJson(User mockUser) {
        try {
            return objectMapper.writeValueAsString(mockUser);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void compareJsonOutput(ResultActions resultActions, User mockUser) throws Exception {
        resultActions
                .andExpect(jsonPath("$.userId", is(mockUser.getUserId())))
                .andExpect(jsonPath("$.firstName", is(mockUser.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(mockUser.getLastName())))
                .andExpect(jsonPath("$.dateOfBirth", is(mockUser.getDateOfBirth())))
                .andExpect(jsonPath("$.email", is(mockUser.getEmail())))
                .andExpect(jsonPath("$.password", is(mockUser.getPassword())));
    }

}
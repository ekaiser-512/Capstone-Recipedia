package org.example.capstonebackend.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.capstonebackend.model.User;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.hamcrest.core.Is.is;


import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;

@Component
public class UserTestUtilities {

    static User user = new User();

    public final User mockUser = createMockUser();

    public static String firstName = "Joe";

    private static String lastName = "Doe";

    private static String dateOfBirth = "02/01/1992";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static User createMockUser() {
        User user = new User();
        user.setId(1);
        user.setFirstName("Joe");
        user.setLastName("Doe");
        user.setDatOfBirth("02/01/1992");
        return user;
    }
    public static String userToJson(User mockUser) {
        try {
            return objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void compareJsonOutput(ResultActions resultActions) throws Exception {
        resultActions
                .andExpect((ResultMatcher) jsonPath("$.id", is(mockUser.getId())))
                .andExpect((ResultMatcher) jsonPath("$.firstName", is(mockUser.getFirstName())))
                .andExpect((ResultMatcher) jsonPath("$.lastName", is(mockUser.getLastName())))
                .andExpect((ResultMatcher) jsonPath("$.dateOfBirth", is(mockUser.getDatOfBirth())));
    }

}
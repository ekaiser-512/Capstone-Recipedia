package org.example.capstonebackend.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.capstonebackend.model.Auth;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
@Component
public class AuthTestUtilities {

    static Auth auth = new Auth();
    public static Auth mockAuth = createMockAuth();

    public static String email = "test@email.com";

    private static String password = "mockPassword";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static Auth createMockAuth() {
        Auth auth = new Auth();
        auth.setId(1);
        auth.setEmail(email);
        auth.setPassword(password);
        return auth;
    }
    public static String authToJson(Auth mockAuth) {
        try {
            return objectMapper.writeValueAsString(mockAuth);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void compareJsonOutput(ResultActions resultActions, Auth mockAuth) throws Exception {
        resultActions
                .andExpect(jsonPath("$.id", is(mockAuth.getId())))
                .andExpect(jsonPath("$.email", is(mockAuth.getEmail())))
                .andExpect(jsonPath("$.password", is(mockAuth.getPassword())));
    }

}

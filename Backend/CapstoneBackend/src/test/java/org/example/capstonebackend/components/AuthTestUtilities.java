package org.example.capstonebackend.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.capstonebackend.model.Auth;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.hamcrest.core.Is.is;


import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;

@Component
public class AuthTestUtilities {

    static Auth auth = new Auth();

    public final Auth mockAuth = createMockAuth();

    public static String email = "test@email.com";

    private static String password = "mockPassword";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static Auth createMockAuth() {
        Auth auth = new Auth();
        auth.setAuthId(1);
        auth.setUserEmail(email);
        auth.setUserPassword(password);

        return auth;
    }
    public static String authToJson(Auth mockAuth) {
        try {
            return objectMapper.writeValueAsString(auth);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void compareJsonOutput(ResultActions resultActions) throws Exception {
        resultActions
                .andExpect((ResultMatcher) jsonPath("$.authId", is(mockAuth.getAuthId())))
                .andExpect((ResultMatcher) jsonPath("$.userEmail", is(mockAuth.getUserEmail())))
                .andExpect((ResultMatcher) jsonPath("$.userPassword", is(mockAuth.getUserPassword())));
    }

}

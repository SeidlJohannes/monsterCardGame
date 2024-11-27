package at.fhtw.mcg.service;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.mcg.model.User;
import at.fhtw.mcg.persistence.UnitOfWork;
import at.fhtw.mcg.persistence.repository.UserRepository;
import at.fhtw.mcg.persistence.repository.UserRepositoryImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class SessionService extends AbstractService {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SessionService() {
        userRepository = new UserRepositoryImpl(new UnitOfWork());
    }

    // GET /user(:id
    public Response getSession(String id)
    {
        System.out.println("get weather for id: " + id);
        User weather = userRepository.findById(Integer.parseInt(id));
        String json = null;
        try {
            json = this.getObjectMapper().writeValueAsString(weather);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new Response(HttpStatus.OK, ContentType.JSON, json);
    }
    // GET /user
    public Response getUser() {
        return new Response(HttpStatus.NOT_IMPLEMENTED);
    }

    // POST /user
    public Response login(Request request) {
        System.out.println("Login attempt"); // Debug log
        String body = request.getBody();

        // Check if request body is empty
        if (body == null || body.isEmpty()) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\": \"Request body is empty\"}");
        }

        try {
            // Parse the JSON body into a User object
            User loginUser = objectMapper.readValue(body, User.class);

            // Validate username and password
            if (loginUser.getUsername() == null || loginUser.getUsername().isEmpty()) {
                return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\": \"Username is missing or empty\"}");
            }
            if (loginUser.getPassword() == null || loginUser.getPassword().isEmpty()) {
                return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\": \"Password is missing or empty\"}");
            }

            // Check if the username and password match an entry in userList
            User existingUser = userRepository.findByUsername(loginUser.getUsername());
            if (existingUser == null || !existingUser.getPassword().equals(loginUser.getPassword())) {
                return new Response(HttpStatus.UNAUTHORIZED, ContentType.JSON, "{\"error\": \"Invalid username or password\"}");
            }

            // Generate token in the format "username-mtcgToken"
            String token = existingUser.getUsername() + "-mtcgToken";

            // Return token as JSON response
            String jsonResponse = "{\"token\": \"" + token + "\"}";
            return new Response(HttpStatus.OK, ContentType.JSON, jsonResponse);

        } catch (IOException e) {
            // Handle JSON parsing errors
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\": \"Invalid JSON format\"}");
        } catch (Exception e) {
            // General error handling
            e.printStackTrace(); // Log stack trace for debugging
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR, ContentType.JSON, "{\"error\": \"An error occurred\"}");
        }
    }

    // GET /user
    public Response getSessionPerRepository() {
        return new Response(HttpStatus.OK, ContentType.PLAIN_TEXT, "SESSION REQUEST");
    }
}
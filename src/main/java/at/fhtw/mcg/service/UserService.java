package at.fhtw.mcg.service;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.mcg.model.Card;
import at.fhtw.mcg.model.User;
import at.fhtw.mcg.persistence.UnitOfWork;
import at.fhtw.mcg.persistence.repository.UserRepository;
import at.fhtw.mcg.persistence.repository.UserRepositoryImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class UserService extends AbstractService {

    private ObjectMapper objectMapper = new ObjectMapper();
    private final UserRepository userRepository = new UserRepositoryImpl(new UnitOfWork());

    

    // GET /user(:id
    public Response getUser(String id)
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
    public Response addUser(Request request) {
        System.out.println("addUserToRepository"); // Debug-Ausgabe
        String body = request.getBody();

        // Prüfen, ob der Body leer ist
        if (body == null || body.isEmpty()) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\": \"Request body is empty\"}");
        }

        try {
            // JSON-Body in ein User-Objekt parsen
            User newUser = objectMapper.readValue(body, User.class);

            // Validieren des Users
            if (newUser.getUsername() == null || newUser.getUsername().isEmpty()) {
                return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\": \"Username is missing or empty\"}");
            }
            if (newUser.getPassword() == null || newUser.getPassword().isEmpty()) {
                return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\": \"Password is missing or empty\"}");
            }

            // Überprüfen, ob der Benutzername bereits existiert
            if (userRepository.findByUsername(newUser.getUsername()) != null) {
                return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\": \"User already exists\"}");
            }

            // Speichern des neuen Users
            User savedUser = userRepository.saveUser(newUser);

            // Erfolgsantwort erstellen
            String jsonResponse = objectMapper.writeValueAsString(savedUser);
            return new Response(HttpStatus.CREATED, ContentType.JSON, jsonResponse);

        } catch (IOException e) {
            // Fehler beim Parsen des JSON-Bodys behandeln
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\": \"Invalid JSON format\"}");
        } catch (Exception e) {
            // Generische Fehlerbehandlung
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR, ContentType.JSON, "{\"error\": \"An error occurred\"}");
        }
    }

    // GET /user
    public Response getUserPerRepository() {
        System.out.println("getUserPerRepository"); //print in main
        //User user = new User(1,"user1","pw1", 20,new ArrayList<Card>(),new ArrayList<Card>(),1111);
        String json = null;
        try {
            Collection<User> userJson = userRepository.findAllUser();
            //userJson.stream().forEach(user1 -> System.out.println(user1));
            json = this.getObjectMapper().writeValueAsString(userJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return new Response(HttpStatus.OK, ContentType.JSON, json);
    }
}

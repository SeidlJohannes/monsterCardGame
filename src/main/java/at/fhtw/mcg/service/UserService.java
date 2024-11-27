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
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class UserService extends AbstractService {

    private final UserRepository userRepository;

    public UserService() {
        userRepository = new UserRepositoryImpl(new UnitOfWork());
    }

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
        return new Response(HttpStatus.NOT_IMPLEMENTED);
    }

    // GET /user
    public Response getUserPerRepository() {
        System.out.println("getUserPerRepository"); //print in main
        User user = new User(1,"user1","pw1", 20,new ArrayList<Card>(),new ArrayList<Card>(),1111);
        String json = null;
        try {
            List<User> userJson = userRepository.findAllUser();
            //userJson.stream().forEach(user1 -> System.out.println(user1));
            json = this.getObjectMapper().writeValueAsString(userJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new Response(HttpStatus.OK, ContentType.JSON, json);
    }
}

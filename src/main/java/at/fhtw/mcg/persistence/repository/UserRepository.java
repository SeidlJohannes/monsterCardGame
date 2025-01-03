package at.fhtw.mcg.persistence.repository;

import at.fhtw.httpserver.server.Request;
import at.fhtw.mcg.model.User;

import java.util.Collection;
import java.util.List;

public interface UserRepository {


    User findById(int id);
    Collection<User> findAllUser();
    User findByUsername(String username);
    boolean existsByUsername(String username);
    User saveUser(User user);
    void addToken(String username, String token);
}

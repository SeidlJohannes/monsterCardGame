package at.fhtw.mcg.persistence.repository;

import at.fhtw.mcg.model.User;

import java.util.Collection;
import java.util.List;

public interface UserRepository {


    User findById(int id);
    List<User> findAllUser();
    //void create(Weather weather);

    User saveUser(User user);
}

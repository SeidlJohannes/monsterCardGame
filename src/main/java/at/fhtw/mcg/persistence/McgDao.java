package at.fhtw.mcg.persistence;

import at.fhtw.mcg.model.Card;
import at.fhtw.mcg.model.User;

import java.util.ArrayList;
import java.util.List;

public class McgDao {
    private List<User> userList;

    public McgDao() {
        userList = new ArrayList<>();
        userList.add(new User(1,"user1","pw1", 20,new ArrayList<Card>(),new ArrayList<Card>(),"1111"));
        userList.add(new User(2,"user2","pw2", 20,new ArrayList<Card>(),new ArrayList<Card>(),"2222"));
        userList.add(new User(3,"user3","pw3", 20,new ArrayList<Card>(),new ArrayList<Card>(),"3333"));
    }

    // GET /weather/:id
    public User getUser(Integer ID) {
        User foundUser = userList.stream()
                .filter(waether -> ID == waether.getId())
                .findAny()
                .orElse(null);

        return foundUser;
    }

    // GET /weather
    public List<User> getUser() {
        return userList;
    }

    // POST /user
    public void addWeather(User user) {
        userList.add(user);
    }
}

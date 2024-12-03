package at.fhtw.mcg.persistence.repository;

import at.fhtw.httpserver.server.Request;
import at.fhtw.mcg.model.Card;
import at.fhtw.mcg.model.User;
import at.fhtw.mcg.persistence.DataAccessException;
import at.fhtw.mcg.persistence.UnitOfWork;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private List<User> userList = new ArrayList<>();


    private UnitOfWork unitOfWork;

    public UserRepositoryImpl(UnitOfWork unitOfWork)
    {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public User findById(int id) {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                    select * from public.weather
                    where id = ?
                """))
        {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            User weather = null;
            while(resultSet.next())
            {
                weather = new User(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        (ArrayList<Card>) resultSet.getArray(20),
                        (ArrayList<Card>) resultSet.getArray(4),
                        resultSet.getString(1234));
            }
            return weather;
        } catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
    }

    @Override
    public List<User> findAllUser() {
        return userList;
        /*try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                    select * from weather
                    where region = ?
                """))
        {
            preparedStatement.setString(1, "Europe");
            ResultSet resultSet = preparedStatement.executeQuery();
            Collection<User> weatherRows = new ArrayList<>();
            while(resultSet.next())
            {
                User weather = new User(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        (ArrayList<Card>) resultSet.getArray(20),
                        (ArrayList<Card>) resultSet.getArray(4),
                        resultSet.getFloat(1234));
                weatherRows.add(weather);
            }

            return weatherRows;
        } catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }*/
    }

    @Override
    public boolean existsByUsername(String username) {
        return userList.stream()
                .anyMatch(user -> user.getUsername().equalsIgnoreCase(username));
    }

    @Override
    public User findByUsername(String username) {
        if (username == null || username.isEmpty()) {
            System.out.println("Invalid username provided for lookup.");
            return null;
        }

        //System.out.println("Looking for username: " + username); //Debug

        //userList.forEach(user1 -> System.out.println("Stored usernames: " + username)); //Debug

        // Search for the user in the list
        for (User user : userList) {
            //System.out.println("Comparing with stored username: " + user.getUsername()); //Debug
            if (user.getUsername().equalsIgnoreCase(username)) {
                //System.out.println("Match found for username: " + username); //Debug
                return user;
            }
        }

        //System.out.println("No match found for username: " + username); //Debug
        return null;
    }



    @Override
    public User saveUser(User user) {
        //userList.forEach(user1 -> System.out.println("Stored username: " + user.getUsername())); //Debug
        userList.add(user);
        System.out.println("User is registered");
        //userList.forEach(user1 -> System.out.println("Stored username: " + user.getUsername())); //Debug
        return user;
    }

    @Override
    public void addToken(String username, String token) {
        User foundUser = findByUsername(username);
        foundUser.setToken(token);
    }
}

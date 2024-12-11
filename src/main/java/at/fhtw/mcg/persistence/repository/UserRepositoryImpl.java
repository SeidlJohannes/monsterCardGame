package at.fhtw.mcg.persistence.repository;

import at.fhtw.httpserver.server.Request;
import at.fhtw.mcg.model.Card;
import at.fhtw.mcg.model.User;
import at.fhtw.mcg.persistence.DataAccessException;
import at.fhtw.mcg.persistence.UnitOfWork;
import at.fhtw.sampleapp.persistence.repository.WeatherRepository;
import at.fhtw.sampleapp.persistence.repository.WeatherRepositoryImpl;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
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
        return userList.get(1);
    }

    @Override
    public Collection<User> findAllUser() {

        //return userList;
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                    select * from users
                """))
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            Collection<User> userCollection = new ArrayList<>();

            while (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("userID"),      // Column 1: userID
                        resultSet.getString("username"), // Column 2: username
                        resultSet.getString("password"), // Column 3: password
                        resultSet.getInt("coins"),       // Column 4: coins
                        resultSet.getString("token")     // Column 5: token
                );
                userCollection.add(user);
            }

            System.out.println("All users selected");
            return userCollection;
        } catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        return userList.stream()
                .anyMatch(user -> user.getUsername().equalsIgnoreCase(username));
    }

    @Override
    public User findByUsername(String username) {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                    SELECT * FROM users
                    WHERE LOWER(username) = LOWER(?)
                """)) {
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) { // Check if a result exists
                User user = new User(
                        resultSet.getInt("userID"),      // Column 1: userID
                        resultSet.getString("username"), // Column 2: username
                        resultSet.getString("password"), // Column 3: password
                        resultSet.getInt("coins"),       // Column 4: coins
                        resultSet.getString("token")     // Column 5: token
                );
                System.out.println("User found: " + username);
                return user; // Return the found user
            } else {
                System.out.println("No user with the given username found");
                return null; // Return null if no user is found
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to fetch user by username", e);
        }
    }


    @Override
    public User saveUser(User user) {
        //System.out.println("saveUser() entered"); //Debug
        //userList.forEach(user1 -> System.out.println("Stored username: " + user.getUsername())); //Debug
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                    INSERT INTO users (userID, username, password, coins)
                    VALUES(?,?,?,?)
                """))
        {
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, 20);
            //preparedStatement.setString(5, user.getPassword() + "-mtcgToken");
            int rowsAffected  = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User is registered");
            } else {
                System.out.println("No rows were inserted");
            }
        } catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
        //userList.forEach(user1 -> System.out.println("Stored username: " + user.getUsername())); //Debug
        return user;
    }

    @Override
    public void addToken(String username, String token) {
        User foundUser = findByUsername(username);
        foundUser.setToken(token);
    }
}

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

    private List<User> userList;

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
                        resultSet.getFloat(1234));
            }
            return weather;
        } catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
    }

    @Override
    public List<User> findAllUser() {
        return List.of(User.builder().
                id(1).
                username("user1").
                password("pw1").
                build());
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
    public User saveUser(User user) {
        userList.add(user);
        return user;
    }
}

package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection;

    public UserDaoJDBCImpl() {
        this.connection = Util.getConnection();
    }

    public void createUsersTable() {
        String createUsersTableSQL = "CREATE TABLE IF NOT EXISTS users (id INT, name VARCHAR(45), lastname VARCHAR(45), age TINYINT, PRIMARY KEY (id))";
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(createUsersTableSQL);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String dropUsersTableSQL = "DROP TABLE IF EXISTS users";
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(dropUsersTableSQL);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

//    public void saveUser(String name, String lastName, byte age) {
//        String saveUserSQL = "INSERT INTO users (id, name, lastname, age) VALUES (?, ?, ?, ?)";
//        try(PreparedStatement statement = connection.prepareStatement(saveUserSQL, Statement.RETURN_GENERATED_KEYS)){
//            statement.setString(1, name);
//            statement.setString(2, lastName);
//            statement.setInt(3, age);
//            statement.executeUpdate();
//
//            try(ResultSet generatedKeys = statement.getGeneratedKeys()) {
//                if(generatedKeys.next()){
//                    long id = generatedKeys.getInt(1);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//    }
public void saveUser(String name, String lastName, byte age) {
    String sqlInsert = "INSERT INTO users (id, name, lastName, age) VALUES (?, ?, ?, ?)";
    try {
        Long newId = getNextId();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert)) {
            preparedStatement.setLong(1, newId);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, lastName);
            preparedStatement.setByte(4, age);
            preparedStatement.executeUpdate();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    private Long getNextId() {
        String sql = "SELECT MAX(id) FROM users";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                return resultSet.getLong(1) + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1L;
    }
    //the end of getID test's methods

    public void removeUserById(long id) {
        String removeUserByIdSQL = "DELETE FROM users WHERE id = ?";
        try(PreparedStatement statement = connection.prepareStatement(removeUserByIdSQL)){
            statement.setLong(1, id);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String getAllUsersSQL = "SELECT * FROM users";
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(getAllUsersSQL);
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                users.add(user);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        String cleanUsersTableSQL = "DELETE FROM users";
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(cleanUsersTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

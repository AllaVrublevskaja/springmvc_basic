package org.example.dao;

import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDbDao {
    DataSource source;
    private final Connection cnn;

    @Autowired
    public UserDbDao(DataSource source) throws SQLException {
        this.source = source;
        cnn = source.getConnection();
//        Statement st = cnn.createStatement();
    }
     public User findById(int id)  {
        String query = "SELECT * FROM users WHERE id = ?";
        PreparedStatement statement;
        try {
            statement = cnn.prepareStatement(query);
            statement.setInt(1, id);
            statement.execute();

            ResultSet set = statement.getResultSet();
            set.next();
            if (set.getRow()>0)
                return User.builder()
                        .id(set.getInt("id"))
                        .email(set.getString("email"))
                        .password(set.getString("password"))
                        .build();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getAll() {
        String query = "SELECT * FROM users";
        List<User> users = new ArrayList();
        PreparedStatement statement;
        try {
            statement = cnn.prepareStatement(query);
            statement.execute();
            ResultSet set = statement.getResultSet();

            while(set.next()) {
                User user = User.builder()
                        .id(set.getInt("id"))
                        .email(set.getString("email"))
                        .password(set.getString("password"))
                        .build();
                users.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return users;
    }

    public void save(User user) {
        String query = "INSERT INTO users(email, password) VALUES(?, ?)";
        PreparedStatement statement;
        try {
            statement = cnn.prepareStatement(query);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Object delete(int id) {
            String query = "DELETE FROM users WHERE id=?";
            PreparedStatement statement;
            try {
                statement = cnn.prepareStatement(query);
                statement.setInt(1,id);

                statement.execute();
            } catch(SQLException ex) {
                ex.printStackTrace();
            }
        return null;
    }
    public Object update(int id, User user) {
            String query = "UPDATE users SET email = ?, password = ? WHERE id=?";
            PreparedStatement statement;
            try {
                statement = cnn.prepareStatement(query);
                statement.setInt(3,id);
                statement.setString(1, user.getEmail());
                statement.setString(2, user.getPassword());
                statement.toString();
                statement.executeUpdate();
            } catch(SQLException ex) {
                ex.printStackTrace();
            }
        return null;
    }
}

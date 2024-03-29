package overridetech.jdbc.jpa.dao;

import overridetech.jdbc.jpa.model.User;
import overridetech.jdbc.jpa.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getPostgresConnection();
             Statement stmt = connection.createStatement()) {
            stmt.execute("create table if not exists public.users (id bigint primary key generated by default as identity, first_name varchar(256) not null, last_name varchar(256), age smallint)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getPostgresConnection();
             Statement stmt = connection.createStatement()) {
            stmt.execute("drop table if exists users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void saveUser(String name, String lastName, byte age) {
        String sql = "insert into users (first_name,last_name,age) values(?,?,?)";
        try (Connection connection = Util.getPostgresConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getPostgresConnection();
             PreparedStatement ps = connection.prepareStatement("delete from users where id=?")) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        try (Connection connection = Util.getPostgresConnection();
             PreparedStatement ps = connection.prepareStatement("select * from users")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setAge(rs.getByte("age"));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getPostgresConnection();
             Statement stmt = connection.createStatement()) {
            stmt.execute("truncate users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

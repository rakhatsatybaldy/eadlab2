package iitu.ead2.lab2.db;

import iitu.ead2.lab2.entity.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DbManager {
    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ead2_lab1?serverTimezone=UTC&useUnicode=true",  "root" , "");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static boolean addUser(User user){
        int rows = 0;
        try{

            PreparedStatement statement = connection.prepareStatement("" +
                    "INSERT INTO users (name , email , password) " +
                    "VALUES (?, ?, ?)");

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());

            rows = statement.executeUpdate();
            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return rows>0;
    }

    public static User getUserByEmail(String email){

        User user = null;

        try{

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE email = ?");
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                user = new User(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("password")
                );
            }

            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return user;
    }
}

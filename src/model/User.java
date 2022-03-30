package model;

/** This class creates User objects. Each User object represents one user existing in the database. */
public class User {


    private int userId;


    private String userName;


    private String password;



    public User(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }


    public int getUserId() {
        return userId;
    }


    public void setUserId(int userId) {
        this.userId = userId;
    }


    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }


    @Override
    public String toString() {
        return Integer.toString(userId);
    }
}

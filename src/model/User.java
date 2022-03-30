package model;

/** This class creates User objects. Each User object represents one user existing in the database. */
public class User {

    /** Stores the user's ID number. */
    private int userId;

    /** Stores the user's username. */
    private String userName;

    /** Stores the user's password. */
    private String password;


    /** Initializes the User's object with a parameter for each member of the User class */
    public User(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    /** This function gets a User's ID number.
     * @return a User's ID number */
    public int getUserId() {
        return userId;
    }

    /** This function sets a User's ID number.
     * @param userId is a User's ID number */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /** This function gets a User's name.
     * @return a User's name */
    public String getUserName() {
        return userName;
    }

    /** This function sets a User's name.
     * @param userName a User's name */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** This function overrides the toString method so that it returns the ID of the user.
     * @return the id of the user as a String object */
    @Override
    public String toString() {
        return Integer.toString(userId);
    }
}

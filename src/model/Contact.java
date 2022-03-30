package model;

/** This class creates Contact objects. Each Contact object represents one contact existing in the database. */
public class Contact {

    /** Stores a contact's ID number. */
    private int contactId;

    /** Stores a contact's name. */
    private String contactName;

    /** Stores a contact's email. */
    private String email;


    /** Initializes a contact with an ID, name, and email. */
    public Contact(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    /** This function gets a contact's ID number.
     * @return a contact's ID number */
    public int getContactId() {
        return contactId;
    }

    /** This function sets a contact's ID number.
     * @param contactId sets a contact's ID number */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /** This function overrides the toString method so that it returns the contactName.
     * @return the name of the contact */
    @Override
    public String toString() {
        return contactName;
    }
}

package model;

import java.time.LocalDateTime;

/** This class creates Customer objects. Each Customer object represents one customer existing in the database. */
public class Customer {

    /** Stores a customer's ID number. */
    private int customerId;

    /** Stores a customer's name. */
    private String customerName;

    /** Stores a customer's address. */
    private String address;

    /** Stores a customer's postal code. */
    private String postalCode;

    /** Stores a customer's phone number. */
    private String phoneNumber;

    /** Stores the date and time a customer was created in the database. */
    private LocalDateTime createDate;

    /** Stores who created a customer in the database. */
    private String createdBy;

    /** Stores the date and time a customer was last updated in the database. */
    private LocalDateTime lastUpdate;

    /** Stores who last updated a customer in the database. */
    private String lastUpdatedBy;

    /** Stores the ID for the first-level-division a customer lives in. */
    private int divisionId;

    /** Stores the ID for the country a customer lives in. */
    private int countryId;

    /** Stores the name of the first-level-division a customer lives in. */
    private String divisionName;

    /** Stores the name of the country a customer lives in. */
    private String countryName;


    /** Initializes a customer object with a parameter for each member of the Customer class. */
    public Customer(int customerId, String customerName, String address, String postalCode, String phoneNumber,
                    LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy,
                    int divisionId, int countryId, String divisionName, String countryName) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionId = divisionId;
        this.countryId = countryId;
        this.divisionName = divisionName;
        this.countryName = countryName;
    }


    /** This function gets a customer's ID number.
     * @return a customer's ID number */
    public int getCustomerId() {
        return customerId;
    }

    /** This function sets a customer's ID number.
     * @param customerId sets a customer's ID number */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /** This function gets a customer's name.
     * @return a customer's name */
    public String getCustomerName() {
        return customerName;
    }

    /** This function gets a customer's address.
     * @return a customer's address */
    public String getAddress() {
        return address;
    }

    /** This function gets a customer's postal code.
     * @return a customer's postal code */
    public String getPostalCode() {
        return postalCode;
    }

    /** This function gets a customer's phone number.
     * @return a customer's phone number */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /** This function gets the date and time a customer was last updated.
     * @return the date and time a customer was last updated */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /** This function sets the date and time a customer was last updated.
     * @param lastUpdate sets the date and time a customer was last updated */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /** This function gets the ID for the division a customer lives in.
     * @return the ID for the division a customer lives in */
    public int getDivisionId() {
        return divisionId;
    }

    /** This function gets the ID for the country a customer lives in.
     * @return the ID for the country a customer lives in */
    public int getCountryId() {
        return countryId;
    }


    /** This function overrides the toString method so that it returns the name of the customer.
     * @return the name of the customer */
    @Override
    public String toString() {
        return customerName;
    }
}

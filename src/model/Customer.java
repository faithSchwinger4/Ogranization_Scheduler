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
     * @param customerId a customer's ID number */
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


    /** This function gets the name of the customer.
     * @param customerName  the name of the customer */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /** This sets gets the address of the customer.
     * @param address the address of the customer */
    public void setAddress(String address) {
        this.address = address;
    }

    /** This function sets the customer's postal code.
     * @param postalCode the customer's postal code */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /** This function sets the customer's phone number.
     * @param phoneNumber the customer's phone number */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /** This function gets the date and time an appointment was created
     * @return gets the date and time an appointment was created */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /** This function sets the date and time an appointment was created
     * @param createDate the date and time an appointment was created */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /** This function gets the name of whoever created a customer
     * @return the name of whoever created a customer */
    public String getCreatedBy() {
        return createdBy;
    }

    /** This function sets the name of whoever created an appointment
     * @param createdBy the name of whoever created an appointment */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /** This function gets the name of whoever last updated an appointment
     * @return gets the name of whoever last updated an appointment */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /** This function sets the name of whoever last updated an appointment
     * @param lastUpdatedBy name of whoever last updated an appointment */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /** This function sets the division ID associated with a customer.
     * @param divisionId the customer ID associated with a customer */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /** This function sets the country ID associated with a customer.
     * @param countryId the country ID associated with a customer */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /** This function gets division ID where the customer lives.
     * @return the division ID where the customer lives */
    public String getDivisionName() {
        return divisionName;
    }

    /** This function sets the division ID where the customer lives.
     * @param divisionName the division ID where the customer lives */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /** This function gets name of the country where the customer lives.
     * @return the name of the country where the customer lives */
    public String getCountryName() {
        return countryName;
    }

    /** This function sets name of the country where the customer lives.
     * @param countryName the name of the country where the customer lives */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }


    /** This function overrides the toString method so that it returns the name of the customer.
     * @return the name of the customer */
    @Override
    public String toString() {
        return customerName;
    }
}

package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/** This class creates Country objects. Each Country object represents one country existing in the database. */
public class Country {

    /** Stores a country's ID number. */
    private int countryId;

    /** Stores a country's name. */
    private String countryName;

    /** Stores the date and time a country was created in the database. */
    private Timestamp createDate;

    /** Stores who created a country in the database. */
    private String createdBy;

    /** Stores the date and time a country was last updated in the database. */
    private LocalDateTime lastUpdate;

    /** Stores who last updated a country in the database. */
    private String lastUpdatedBy;


    /** Initializes a country object with a parameter for each member of the Country class. */
    public Country(int countryId, String countryName, Timestamp createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy) {
        this.countryId = countryId;
        this.countryName = countryName;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /** This function gets a country's ID number.
     * @return a country's ID number */
    public int getCountryId() {
        return countryId;
    }

    /** This method gets the last date and time the country was updated in the database.
     * @return the last date and time the country was updated */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /** This method sets the last date and time the country was updated in the database.
     * @param lastUpdate the last date and time the country was updated */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }


    /** This function overrides the toString method so that it returns the country's name.
     * @return the name of the country */
    @Override
    public String toString() {
        return countryName;
    }
}

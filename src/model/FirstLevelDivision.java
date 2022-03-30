package model;

import java.time.LocalDateTime;

/** This class creates FirstLevelDivision objects. Each FirstLevelDivision object represents one first-level-division
 * existing in the database. */
public class FirstLevelDivision {

    /** Stores a first-level-division's ID number. */
    private int divisionId;

    /** Stores a first-level-division's name. */
    private String division;

    /** Stores the date and time a first-level-division was created in the database. */
    private LocalDateTime createDate;

    /** Stores who created the first-level-division in the database. */
    private String createdBy;

    /** Stores the date and time a first-level-division was last updated in the database. */
    private LocalDateTime lastUpdate;

    /** Stores who last updated the first-level-division in the database. */
    private String lastUpdatedBy;

    /** Stores a first-level-division's country ID number. */
    private int countryId;


    /** Initializes a FirstLevelDivision object with a parameter for each member of the FirstLevelDivision class. */
    public FirstLevelDivision(int divisionId, String division, LocalDateTime createDate, String createdBy,
                              LocalDateTime lastUpdate, String lastUpdatedBy, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryId = countryId;
    }

    /** This function gets a first-level-division's ID number.
     * @return a first-level-division's ID number */
    public int getDivisionId() {
        return divisionId;
    }

    /** This function gets the date and time a first-level-division's was last updated.
     * @return the date and time a first-level-division's was last updated */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /** This function sets the date and time a first-level-division's was last updated.
     * @param lastUpdate sets the date and time a first-level-division's was last updated */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /** This function sets a first-level-division's ID number.
     * @param divisionId a first-level-division's ID number */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /** This function gets a first-level-division's name.
     * @return a first-level-division's name */
    public String getDivision() {
        return division;
    }

    /** This function sets a first-level-division's name.
     * @param division a first-level-division's name */
    public void setDivision(String division) {
        this.division = division;
    }

    /** This function gets the date and time a first-level-division's was created.
     * @return the date and time a first-level-division's was created */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /** This function sets the date and time a first-level-division's was created.
     * @param createDate the date and time a first-level-division's was created */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /** This function gets the name of whoever created a first-level-division.
     * @return the name of whoever created a first-level-division */
    public String getCreatedBy() {
        return createdBy;
    }

    /** This function sets the name of whoever created a first-level-division.
     * @param createdBy the name of whoever created a first-level-division */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /** This function gets the name of whoever last updated a first-level-division.
     * @return the name of whoever last updated a first-level-division */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /** This function sets the name of whoever last updated a first-level-division.
     * @param lastUpdatedBy the name of whoever last updated a first-level-division */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /** This function gets the ID for the country a first-level-division is in.
     * @return the ID for the country a first-level-division lives in */
    public int getCountryId() {
        return countryId;
    }

    /** This function sets the ID for the country a first-level-division is in.
     * @return the ID for the country a first-level-division lives in */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /** This function overrides the toString method so that it returns the name of the first-level-division.
     * @return the name of the first-level-division */
    @Override
    public String toString() {
        return division;
    }
}

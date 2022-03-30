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


    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getCountryId() {
        return countryId;
    }

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

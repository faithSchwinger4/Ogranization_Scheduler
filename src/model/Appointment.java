package model;

import java.time.LocalDateTime;

/** This class creates Appointment objects. Each Appointment object represents one appointment existing in the database. */
public class Appointment {

    /** Stores an appointment's ID number. */
    private int appointmentId;

    /** Stores an appointment's title. */
    private String title;

    /** Stores an appointment's description. */
    private String description;

    /** Stores an appointment's location. */
    private String location;

    /** Stores an appointment's type. */
    private String type;

    /** Stores an appointment's start date and time. */
    private LocalDateTime start;

    /** Stores an appointment's end date and time. */
    private LocalDateTime end;

    /** Stores the date and time an appointment was created. */
    private LocalDateTime createDate;

    /** Stores who created an appointment. */
    private String createdBy;

    /** Stores the date and time an appointment was last updated. */
    private LocalDateTime lastUpdate;

    /** Stores who last updated an appointment. */
    private String lastUpdatedBy;

    /** Stores the customer ID associated with an appointment. */
    private int customerId;

    /** Stores the user ID associated with an appointment. */
    private int userId;

    /** Stores the contact ID associated with an appointment. */
    private int contactId;

    /** Stores the contact name associated with an appointment. */
    private String contactName;

    /** Initializes the appointment object with a parameter for each member of the class. */
    public Appointment(int appointmentId, String title, String description, String location, String type,
                       LocalDateTime start, LocalDateTime end, LocalDateTime createDate, String createdBy,
                       LocalDateTime lastUpdate, String lastUpdatedBy, int customerId, int userId, int contactId,
                       String contactName) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
        this.contactName = contactName;
    }

    /** This function gets the appointment ID for an appointment.
     * @return the ID number for an appointment */
    public int getAppointmentId() {
        return appointmentId;
    }

    /** This function gets the title of an appointment
     * @return the title of an appointment */
    public String getTitle() {
        return title;
    }

    /** This function sets the title of an appointment.
     * @param title the title to be set for an appointment */
    public void setTitle(String title) {
        this.title = title;
    }

    /** This function gets the description of an appointment.
     * @return the description of an appointment */
    public String getDescription() {
        return description;
    }

    /** This function gets the location of an appointment.
     * @return the location of an appointment */
    public String getLocation() {
        return location;
    }

    /** This function sets the location of an appointment.
     * @param location sets the location of an appointment */
    public void setLocation(String location) {
        this.location = location;
    }

    /** This function gets the type of appointment.
     * @return the type of appointment */
    public String getType() {
        return type;
    }

    /** This function sets the type of appointment.
     * @param type sets the type of appointment */
    public void setType(String type) {
        this.type = type;
    }

    /** This function gets the start date and time of an appointment.
     * @return the start date and time of an appointment */
    public LocalDateTime getStart() {
        return start;
    }

    /** This function sets the start date and time of an appointment.
     * @param start sets the start date and time of an appointment */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /** This function gets the end date and time of an appointment.
     * @return the end date and time of an appointment */
    public LocalDateTime getEnd() {
        return end;
    }

    /** This function sets the end date and time of an appointment.
     * @param end sets the end date and time of an appointment */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /** This function gets the date and time of the last update of an appointment.
     * @return the start date and time of the last update of an appointment */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /** This function sets the date and time of the last update of an appointment.
     * @param lastUpdate sets the start date and time of the last update of an appointment */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /** This function gets the customer ID associated with an appointment.
     * @return the customer ID associated with an appointment */
    public int getCustomerId() {
        return customerId;
    }

    /** This function sets the customer ID associated with an appointment.
     * @param customerId the customer ID to be set to an appointment */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /** This function gets the user ID associated with an appointment.
     * @return the user ID associated with an appointment */
    public int getUserId() {
        return userId;
    }

    /** This function gets the contact ID associated with an appointment.
     * @return the contact ID associated with an appointment */
    public int getContactId() {
        return contactId;
    }
}

package utility;

import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.SQLException;
import java.time.*;

/** This class holds methods used to validate the times a user attempts to assign to an appointment. */
public class AppointmentTimeValidation {

    /** This function checks if a potential appointment scheduling conflicts with any existing appointments a customer
     * has in the database. If there is a conflict it returns false, if there's no conflict it returns true.
     * @param customerId the ID of the customer the appointment is potentially being made for
     * @param start the potential start date and time of an appointment
     * @param end the potential end date and time of an appointment
     * @param appointment the potential appointment data stored in an Appointment object
     * @return true id there's no conflicting appointment, false if there is a conflicting appointment */
    public static boolean noConflictingAppointment(Appointment appointment, int customerId, LocalDateTime start, LocalDateTime end) throws SQLException {
        // get all the appointments for once customer
        ObservableList<Appointment> customerAppointments = AppointmentQuery.getCustomerAppointments(customerId);
        if (appointment == null) {
            // skip this step
            System.out.println("Skip else, appointment null");
        }
        else {
            customerAppointments = AppointmentQuery.getAllAppointmentsExceptChosen(appointment.getAppointmentId());
            if (customerAppointments.contains(appointment)) {
                System.out.println("List contains correct item");
            }
            for (Appointment eachAppointment : customerAppointments) {
                System.out.println(eachAppointment.getAppointmentId());
            }
        }

        for (Appointment appointment1 : customerAppointments) {
            if (start.isAfter(appointment1.getStart()) && start.isBefore(appointment1.getEnd())) {
                return false;
            }
            if (start.isEqual(appointment1.getStart()) || end.isEqual(appointment1.getEnd())) {
                return false;
            }
            if (end.isAfter(appointment1.getStart()) && end.isBefore(appointment1.getEnd())) {
                return false;
            }
            if (start.isBefore(appointment1.getStart()) && end.isAfter(appointment1.getEnd())) {
                return false;
            }
        }

        //there was no conflict, so it can continue
        return true;
    }

    /** This function determines if an appointment is being scheduled during business hours or not.
     * @param end the potential end date and time of an appointment
     * @param start the potential start date and time of an appointment
     * @return true if the appointment is being scheduled during business hours, false if it is being scheduled outside business hours */
    public static boolean duringBusinessHours(LocalDateTime start, LocalDateTime end) {
        // check the time zone against EST
        // get the computers current time zone?\
        ZoneId currentZoneId = ZoneId.systemDefault();

        LocalTime convertedStartTime = convertToEST(start);
        LocalTime convertedEndTime = convertToEST(end);
        LocalTime businessOpens = LocalTime.of(8,0);
        LocalTime businessCloses = LocalTime.of(22,0);

        if (convertedStartTime.isBefore(businessOpens) || convertedStartTime.isAfter(businessCloses)) {
            return false;
        }
        if (convertedEndTime.isBefore(businessOpens) || convertedEndTime.isAfter(businessCloses)) {
            return false;
        }
        if (convertedEndTime.equals(businessOpens) || convertedStartTime.equals(businessCloses)) {
            return false;
        }

        return true;
    }

    /** This function helps the one checking appointment times against business hours by converting any localDateTime object
     * to the corresponding localDateTime object in EST.
     * @param localDateTime the localDateTime object to be converted
     * @return the localDateTime object converted to EST */
    public static LocalTime convertToEST(LocalDateTime localDateTime) {
        //convert localDateTime being checked to zonedDateTime in current time zone
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());

        Instant localToUTCInstant = zonedDateTime.toInstant();
        ZonedDateTime localUTCToEST = localToUTCInstant.atZone(ZoneId.of("America/New_York"));

        LocalDateTime convertedToEST = localUTCToEST.toLocalDateTime();
        LocalTime convertedTime = convertedToEST.toLocalTime();

        return convertedTime;
    }

}

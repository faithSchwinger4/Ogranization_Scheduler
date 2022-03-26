package utility;

import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.SQLException;
import java.time.*;

public class AppointmentTimeValidation {

    public static boolean noConflictingAppointment(int customerId, LocalDateTime start, LocalDateTime end) throws SQLException {
        // get all the appointments for once customer
        ObservableList<Appointment> customerAppointments = AppointmentQuery.getCustomerAppointments(customerId);

        for (Appointment appointment : customerAppointments) {
            if (start.isAfter(appointment.getStart()) && start.isBefore(appointment.getEnd())) {
                return false;
            }
            if (start.isEqual(appointment.getStart()) || end.isEqual(appointment.getEnd())) {
                return false;
            }
            if (end.isAfter(appointment.getStart()) && end.isBefore(appointment.getEnd())) {
                return false;
            }
        }

        //there was no conflict, so it can continue
        return true;
    }

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

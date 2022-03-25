package utility;

import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.SQLException;
import java.time.LocalDateTime;

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
        // get the computers current time zone?
        //if () {}

        return true;
    }

}
